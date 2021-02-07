package wisp.questgiver

import com.fs.starfarer.api.Global
import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.campaign.InteractionDialogPlugin
import com.fs.starfarer.api.campaign.rules.MemoryAPI
import com.fs.starfarer.api.combat.EngagementResultAPI
import com.fs.starfarer.api.ui.LabelAPI
import com.fs.starfarer.api.util.Misc
import kotlinx.coroutines.*
import wisp.questgiver.wispLib.ServiceLocator
import java.awt.Color
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class InteractionDefinition<S : InteractionDefinition<S>>(
    @Transient var onInteractionStarted: suspend S.() -> Unit = {},
    @Transient var pages: List<Page<S>>,
    @Transient private var shouldValidateOnDialogStart: Boolean = true
) {
    class Page<S>(
        val id: Any,
        val image: Image? = null,
        val onPageShown: suspend S.() -> Unit,
        val options: List<Option<S>>
    )

    open class Option<S>(
        val text: S.() -> String,
        val shortcut: Shortcut? = null,
        val showIf: S.() -> Boolean = { true },
        val onOptionSelected: suspend S.(InteractionDefinition<*>.PageNavigator) -> Unit,
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
        shouldValidateOnDialogStart = newInstance.shouldValidateOnDialogStart
        return this
    }

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
        private var continuationOfPausedPage: (suspend () -> Unit)? = null
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
            GlobalScope.launch { page.onPageShown(this@InteractionDefinition as S) }

            if (!isWaitingOnUserToPressContinue) {
                showOptions(page.options)
            }
        }

        /**
         * Show the player a "Continue" button to break up dialog without creating a new Page object.
         */
        fun promptToContinue(continueText: String, continuation: suspend () -> Unit) {
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

            runBlocking { continuation?.invoke() }
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


        internal suspend fun onOptionSelected(optionText: String?, optionData: Any?) {
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
        displayWidth = 128f,
        displayHeight = 128f
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
        displayWidth = 480f,
        displayHeight = 300f
    )

    @Transient
    lateinit var dialog: InteractionDialogAPI
    var navigator = PageNavigator()
        internal set

    private val fullStringRegex
        get() = Regex("(.*?)!PAUSE\\|(.*?)!")

    /**
     * Prints the text returned by [stringMaker] to the dialog's textpanel.
     *
     * **Special codes**
     *
     * `"your text here!PAUSE|Continue!more text"`: Adds a break where the player must choose "Continue" to see the rest of the text.
     *
     * @param stringMaker A function that returns the text to display.
     */
    suspend fun para(
        textColor: Color = Misc.getTextColor(),
        highlightColor: Color = Misc.getHighlightColor(),
        stringMaker: ParagraphText.() -> String
    ): LabelAPI? {
        val text = stringMaker(ParagraphText)
        val matches = fullStringRegex.findAll(text).toList()

        return if (matches.isEmpty()) {
            withContext(Dispatchers.Main) {
                dialog.textPanel.addPara(textColor, highlightColor, stringMaker)
            }
        } else {
            val wholeString = matches.first().groupValues[0]
            val textToShow = matches.first().groupValues[1]
            val continueText = matches.first().groupValues[2]

            val label = withContext(Dispatchers.Main) {
                dialog.textPanel.addPara(textColor, highlightColor) { textToShow }
            }

            suspendCoroutine<Unit> {
                navigator.promptToContinue(continueText) {
                    val remainingText = text.removePrefix(wholeString)

                    if (remainingText.isNotBlank()) {
                        para(textColor, highlightColor) { remainingText }
                    }

                    it.resume(Unit)
                }
            }

            label
        }
    }

    /**
     * Needed so we can figure out which BarEvents are part of this mod
     * when looking at [BarEventManager.getInstance().active.items].
     */
    abstract inner class InteractionDialog : InteractionDialogPlugin

    open fun build(): InteractionDialog = InteractionDialogImpl()

    internal open inner class InteractionDialogImpl : InteractionDialog() {

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
            runBlocking { onInteractionStarted(this@InteractionDefinition as S) }

            if (pages.any()) {
                navigator.showPage(pages.first())
            }
        }

        override fun optionSelected(optionText: String?, optionData: Any?) {
            if (optionText != null) {
                // Print out the text of the option the user just selected
                GlobalScope.launch { para(textColor = Global.getSettings().getColor("buttonText")) { optionText } }
            }

            GlobalScope.launch {
                navigator.onOptionSelected(optionText, optionData)
            }
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

fun InteractionDefinition.Image.spriteName(game: ServiceLocator) = game.settings.getSpriteName(this.category, this.id)