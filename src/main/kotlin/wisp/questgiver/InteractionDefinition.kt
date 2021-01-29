package wisp.questgiver

import com.fs.starfarer.api.Global
import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.campaign.InteractionDialogPlugin
import com.fs.starfarer.api.campaign.rules.MemoryAPI
import com.fs.starfarer.api.combat.EngagementResultAPI
import com.fs.starfarer.api.util.Misc
import kotlinx.coroutines.*
import wisp.questgiver.wispLib.ServiceLocator
import java.awt.Color

abstract class InteractionDefinition<S : InteractionDefinition<S>>(
    @Transient var onInteractionStarted: S.() -> Unit = {},
    @Transient var pages: List<Page<S>>,
    private val shouldValidateOnDialogStart: Boolean = true
) {
    class Page<S>(
        val id: Any,
        val image: Image? = null,
        val onPageShown: S.() -> Unit,
        val options: List<Option<S>>
    )

    open class Option<S>(
        val text: S.() -> String,
        val shortcut: Shortcut? = null,
        val showIf: S.() -> Boolean = { true },
        val onOptionSelected: S.(InteractionDefinition<*>.PageNavigator) -> Unit,
        val id: String = Misc.random.nextInt().toString()
    )

    init {
        if (pages.distinctBy { it.id }.count() != pages.count())
            error("All page ids must have a unique id. Page ids: ${pages.joinToString { it.id.toString() }} Dialog: $this")
    }

    /**
     * Create an instance of the implementing class. We then copy the transient fields in that class
     * to this one in [readResolve], since they do not get created by the deserializer.
     * We cannot use `this::class.java.newInstance()` because then the implementing class is required to have
     * a no-args constructor.
     */
    abstract fun createInstanceOfSelf(): InteractionDefinition<S>

    /**
     * When this class is created by deserializing from a save game,
     * it can't deserialize the anonymous methods, so we mark them as transient,
     * then manually assign them using this method, which gets called automagically
     * by the XStream serializer.
     */
    open fun readResolve(): Any {
        val newInstance = createInstanceOfSelf()
        onInteractionStarted = newInstance.onInteractionStarted
        pages = newInstance.pages
        return this
    }

    //    interface PageNavigator<S> {
//        fun goToPage(pageId: Any)
//        fun gotoPage(page: Page<S>)
//        fun close(hideQuestOfferAfterClose: Boolean)
//    }

    companion object {
        /**
         * Special button data that indicates the dialog page has a break in it to wait for the player to
         * press Continue.
         */
        internal const val CONTINUE_BUTTON_ID = "questgiver_continue_button_id"
    }

    /**
     * Coordinator for dialog page navigation.
     */
    open inner class PageNavigator() {
        /**
         * Function to execute after user presses "Continue" to resume a page.
         */
        private var continuationOfPausedPage: (() -> Unit)? = null
        private var currentPage: Page<S>? = null
        internal val isWaitingOnUserToPressContinue: Boolean
            get() = continuationOfPausedPage != null

        /**
         * Navigates to the specified dialogue page.
         */
        open fun goToPage(pageId: Any) {
            showPage(pages.single { it.id == pageId })
        }

        /**
         * Navigates to the specified dialogue page.
         */
        open fun goToPage(page: Page<S>) {
            showPage(page)
        }

        /**
         * Closes the dialog.
         * @param doNotOfferAgain If true, the prompt will not be displayed in the bar while the player
         *   is still there. If false, allows the player to immediately change their mind and trigger the interaction again.
         */
        open fun close(doNotOfferAgain: Boolean) {
            dialog.dismiss()
        }

        /**
         * Refreshes the page's options without fully re-displaying the page.
         * Useful for showing/hiding certain options after choosing one.
         */
        open fun refreshOptions() {
            dialog.optionPanel.clearOptions()

            if (!isWaitingOnUserToPressContinue) {
                showOptions(currentPage!!.options)
            }
        }

        /**
         * Displays a new page of the dialogue.
         */
        open fun showPage(page: Page<S>) {
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
            page.onPageShown(this@InteractionDefinition as S)

            if (!isWaitingOnUserToPressContinue) {
                showOptions(page.options)
            }
        }

        /**
         * Show the player a "Continue" button to break up dialog without creating a new Page object.
         */
        fun promptToContinue(continueText: String, continuation: () -> Unit) {
            continuationOfPausedPage = continuation
            dialog.optionPanel.clearOptions()

            dialog.optionPanel.addOption(continueText, CONTINUE_BUTTON_ID)
        }

        internal fun onUserPressedContinue() {
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

        internal fun <S : InteractionDefinition<S>> showOptions(options: List<Option<S>>) {
            options
                .filter { it.showIf(this@InteractionDefinition as S) }
                .forEach { option ->
                    dialog.optionPanel.addOption(option.text(this@InteractionDefinition as S), option.id)

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


        internal fun onOptionSelected(optionText: String?, optionData: Any?) {
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

                optionSelected.onOptionSelected(this@InteractionDefinition as S, navigator)
            }
        }
    }

    /**
     * @param code constant from [org.lwjgl.input.Keyboard]
     */
    data class Shortcut(
        val code: Int,
        val holdCtrl: Boolean = false,
        val holdAlt: Boolean = false,
        val holdShift: Boolean = false
    )

    open class Image(
        val category: String,
        val id: String,
        val width: Float,
        val height: Float,
        val xOffset: Float,
        val yOffset: Float,
        val displayWidth: Float,
        val displayHeight: Float
    )

    class Portrait(
        category: String,
        id: String
    ) : Image(
        category = category,
        id = id,
        width = 128f,
        height = 128f,
        xOffset = 0f,
        yOffset = 0f,
        displayHeight = 128f,
        displayWidth = 128f
    )

    class Illustration(
        category: String,
        id: String
    ) : Image(
        category = category,
        id = id,
        width = 640f,
        height = 400f,
        xOffset = 0f,
        yOffset = 0f,
        displayHeight = 480f,
        displayWidth = 300f
    )

    @Transient
    lateinit var dialog: InteractionDialogAPI
    var navigator = PageNavigator()
        internal set

    fun para(
        textColor: Color = Misc.getTextColor(),
        highlightColor: Color = Misc.getHighlightColor(),
        stringMaker: ParagraphText.() -> String
    ) = dialog.textPanel.addPara(textColor, highlightColor, stringMaker)

    @Deprecated("Use [para]`")
    fun addPara(
        textColor: Color = Misc.getTextColor(),
        highlightColor: Color = Misc.getHighlightColor(),
        stringMaker: ParagraphText.() -> String
    ) = para(textColor, highlightColor, stringMaker)

    /**
     * Needed so we can figure out which BarEvents are part of this mod
     * when looking at [BarEventManager.getInstance().active.items].
     */
    abstract inner class InteractionDialog : InteractionDialogPlugin

    fun build(): InteractionDialog {
        return object : InteractionDialog() {

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
                this@InteractionDefinition.dialog = dialog
                onInteractionStarted(this@InteractionDefinition as S)

                if (pages.any()) {
                    navigator.showPage(pages.first())
                }
            }

            override fun optionSelected(optionText: String?, optionData: Any?) {
                if (optionText != null) {
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
}

fun InteractionDefinition.Image.spriteName(game: ServiceLocator) = game.settings.getSpriteName(this.category, this.id)