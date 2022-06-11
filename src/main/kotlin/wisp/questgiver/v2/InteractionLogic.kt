package wisp.questgiver.v2

import com.fs.starfarer.api.Global
import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.campaign.rules.MemoryAPI
import com.fs.starfarer.api.characters.PersonAPI
import com.fs.starfarer.api.combat.EngagementResultAPI
import wisp.questgiver.Questgiver.game
import wisp.questgiver.v2.IInteractionLogic.Companion.CONTINUE_BUTTON_ID
import wisp.questgiver.wispLib.ServiceLocator

typealias OnPageShown<S> = S.() -> Unit
typealias OnOptionSelected<S> = S.(IInteractionLogic.IPageNavigator<S>) -> Unit
typealias OnInteractionStarted<S> = S.() -> Unit

abstract class InteractionLogic<S : IInteractionLogic<S>>(
    @Transient override var onInteractionStarted: OnInteractionStarted<S> = {},
    @Transient override var people: List<PersonAPI>? = null,
    @Transient final override var pages: List<IInteractionLogic.Page<S>>,
    @Transient private var shouldValidateOnDialogStart: Boolean = true
) : IInteractionLogic<S> {

    init {
        if (pages.distinctBy { it.id }.count() != pages.count())
            error("All page ids must have a unique id. Page ids: ${pages.joinToString { it.id.toString() }} Dialog: $this")
    }

    /**
     * When this class is created by deserializing from a save game,
     * it can't deserialize the anonymous methods, so we mark them as transient,
     * then manually assign them using this method, which gets called automagically
     * by the XStream serializer.
     */
    open fun readResolve(): Any {
        val newInstance = createInstanceOfSelf()
        onInteractionStarted = newInstance.onInteractionStarted
        people = newInstance.people
        pages = newInstance.pages
        shouldValidateOnDialogStart = newInstance.shouldValidateOnDialogStart
        return this
    }

    /**
     * Coordinator for dialog page navigation.
     * This is what is exposed to users of Questgiver.
     *
     * Not serialized.
     */
    open class PageNavigator<S : IInteractionLogic<S>>(
        internal var interactionDefinition: IInteractionLogic<S>?
    ) : IInteractionLogic.IPageNavigator<S> {
        private val pages by lazy { interactionDefinition!!.pages }
        private val dialog by lazy { interactionDefinition!!.dialog }

        /**
         * Function to execute after user presses "Continue" to resume a page.
         */
        private var continuationOfPausedPage: (() -> Unit)? = null
        private var currentPage: IInteractionLogic.Page<S>? = null
        internal val isWaitingOnUserToPressContinue: Boolean
            get() = continuationOfPausedPage != null

        /**
         * Navigates to the specified dialogue page.
         */
        override fun goToPage(pageId: Any) {
            showPage(
                pages.singleOrNull { (it.id == pageId) || (it.id.toString() == pageId.toString()) }
                    ?: throw NoSuchElementException(
                        "No page with id '$pageId'." +
                                "\nPages: ${pages.joinToString { "'${it.id}'" }}."
                    )
            )
        }

        /**
         * Navigates to the specified dialogue page.
         */
        override fun goToPage(page: IInteractionLogic.Page<S>) {
            showPage(page)
        }

        /**
         * Closes the dialog.
         * @param doNotOfferAgain If true, the prompt will not be displayed in the bar while the player
         *   is still there. If false, allows the player to immediately change their mind and trigger the interaction again.
         */
        override fun close(doNotOfferAgain: Boolean) {
            dialog.dismiss()
        }

        /**
         * Refreshes the page's options without fully re-displaying the page.
         * Useful for showing/hiding certain options after choosing one.
         */
        override fun refreshOptions() {
            game.logger.d { "Clearing options." }
            dialog.optionPanel.clearOptions()

            if (!isWaitingOnUserToPressContinue) {
                showOptions(currentPage!!.options)
            }
        }

        /**
         * Displays a new page of the dialogue.
         */
        override fun showPage(page: IInteractionLogic.Page<S>) {
            dialog.optionPanel.clearOptions()

            if (page.image != null) {
                dialog.visualPanel.showImagePortion(
                    page.image.category,
                    page.image.id,
                    page.image.width,
                    page.image.height,
                    page.image.xOffset,
                    page.image.yOffset,
                    page.image.displayWidth,
                    page.image.displayHeight
                )
            }

            currentPage = page
            page.onPageShown(interactionDefinition as S)

            if (!isWaitingOnUserToPressContinue) {
                showOptions(page.options)
            }
        }

        /**
         * Show the player a "Continue" button to break up dialog without creating a new Page object.
         */
        override fun promptToContinue(continueText: String, continuation: () -> Unit) {
            continuationOfPausedPage = continuation
            game.logger.d { "Clearing options." }
            dialog.optionPanel.clearOptions()

            game.logger.d { "Adding option $CONTINUE_BUTTON_ID with text '$continueText'." }
            dialog.optionPanel.addOption(continueText, CONTINUE_BUTTON_ID)
        }

        override fun onUserPressedContinue() {
            game.logger.d { "Clearing options." }
            dialog.optionPanel.clearOptions()

            // Save the continuation for execution
            val continuation = continuationOfPausedPage
            // Wipe the field variable because execution of the continuation
            // may set a new field variable (eg if there are nested pauses)
            continuationOfPausedPage = null

            // If we didn't just enter a nested pause, finally show the page options
            if (!isWaitingOnUserToPressContinue) {
                currentPage?.let { showOptions(it.options) }
            }

            continuation?.invoke()
        }

        override fun showOptions(options: List<IInteractionLogic.Option<S>>) {
            options
                .filter { it.showIf(interactionDefinition as S) }
                .forEach { option ->
                    val text = option.text(interactionDefinition as S)
                    game.logger.d { "Adding option ${option.id} with text '$text' and shortcut ${option.shortcut}." }
                    dialog.optionPanel.addOption(text, option.id)

                    if (option.shortcut != null) {
                        dialog.optionPanel.setShortcut(
                            option.id,
                            option.shortcut.code,
                            option.shortcut.holdCtrl,
                            option.shortcut.holdAlt,
                            option.shortcut.holdShift,
                            false
                        )
                    }
                }
        }


        override fun onOptionSelected(optionText: String?, optionData: Any?) {
            // If they pressed continue, resume the dialog interaction
            if (optionData == CONTINUE_BUTTON_ID) {
                onUserPressedContinue()
            } else {
                // Otherwise, look for the option they pressed
                val optionSelected = pages
                    .flatMap { page ->
                        page.options
                            .filter { option -> option.id == optionData }
                    }.singleOrNull()
                    ?: return

                optionSelected.onOptionSelected(interactionDefinition as S, this)
            }
        }

        fun destroy() {
            interactionDefinition = null
        }
    }

    fun destroy() {
        navigator.destroy()
    }

    /**
     * Access to the dialog to assume direct control.
     */
    @Transient
    override lateinit var dialog: InteractionDialogAPI
    final override var navigator = PageNavigator(this)
        internal set

    fun build(): IInteractionLogic.InteractionDialog = InteractionDialogImpl()

    /**
     * Create an instance of the implementing class. We then copy the transient fields in that class
     * to this one in [readResolve], since they do not get created by the deserializer.
     * We cannot use `this::class.java.newInstance()` because then the implementing class is required to have
     * a no-args constructor.
     */
    abstract fun createInstanceOfSelf(): InteractionLogic<S>


    internal open inner class InteractionDialogImpl : IInteractionLogic.InteractionDialog() {
        /**
         * Called when this class is instantiated.
         */
        init {
            if (shouldValidateOnDialogStart) {

            }
        }

        /**
         * Called when the dialog is shown.
         */
        override fun init(dialog: InteractionDialogAPI) {
            this@InteractionLogic.dialog = dialog
            val peopleInner = this@InteractionLogic.people

            if (peopleInner?.getOrNull(0) != null) {
                dialog.visualPanel.showPersonInfo(peopleInner[0], true)
            }

            if (peopleInner?.getOrNull(1) != null) {
                dialog.visualPanel.showSecondPerson(peopleInner[1])
            }

            if (peopleInner?.getOrNull(2) != null) {
                dialog.visualPanel.showThirdPerson(peopleInner[2])
            }

            onInteractionStarted(this@InteractionLogic as S)

            if (pages.any()) {
                navigator.showPage(pages.first())
            }
        }

        override fun optionSelected(optionText: String?, optionData: Any?) {
            if (optionText != null) {
                // Print out the text of the option the user just selected
                para(textColor = Global.getSettings().getColor("buttonText")) { optionText }
            }

            navigator.onOptionSelected(optionText, optionData)
        }

        // Other overrides that are necessary but do nothing
        override fun optionMousedOver(optionText: String?, optionData: Any?) {
        }

        override fun getMemoryMap(): MutableMap<String, MemoryAPI> = mutableMapOf()
        override fun backFromEngagement(battleResult: EngagementResultAPI?) {
        }

        override fun advance(amount: Float) {
        }

        override fun getContext(): Any? = null
    }
}

fun IInteractionLogic.Image.spriteName(game: ServiceLocator) = game.settings.getSpriteName(this.category, this.id)
fun IInteractionLogic.Image.spritePath(game: ServiceLocator) = this.spriteName(game)