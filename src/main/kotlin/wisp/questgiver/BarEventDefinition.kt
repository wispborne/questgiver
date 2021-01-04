package wisp.questgiver

import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.characters.FullName
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventWithPerson

/**
 * Defines a [BaseBarEventWithPerson]. Create the [BaseBarEventWithPerson] by calling [buildBarEvent].
 * @param shouldShowEvent Whether the bar event should be shown in the market
 * @param interactionPrompt Add text/images to the bar to show that this event is present,
 *   e.g. "A man is searching for something in the corner."
 * @param textToStartInteraction The option available to the player to start the event, e.g. "Help the man."
 * @param onInteractionStarted Called when the player chooses to start the bar event.
 * @param pages A list of [wisp.questgiver.InteractionDefinition.Page]s that define the structure of the conversation.
 */
abstract class BarEventDefinition<S : InteractionDefinition<S>>(
    @Transient private var shouldShowEvent: (MarketAPI) -> Boolean,
    @Transient var interactionPrompt: S.() -> Unit,
    @Transient var textToStartInteraction: S.() -> String,
    onInteractionStarted: S.() -> Unit,
    pages: List<Page<S>>,
    val personRank: String? = null,
    val personFaction: String? = null,
    val personPost: String? = null,
    val personPortrait: String? = null,
    val personName: FullName? = null
) : InteractionDefinition<S>(
    onInteractionStarted = onInteractionStarted,
    pages = pages
) {

    lateinit var manOrWoman: String
    lateinit var hisOrHer: String
    lateinit var heOrShe: String
    lateinit var event: BaseBarEventWithPerson

    /**
     * Needed so we can figure out which BarEvents are part of this mod
     * when looking at [BarEventManager.getInstance().active.items].
     */
    abstract inner class BarEvent : BaseBarEventWithPerson()

    /**
     * When this class is created by deserializing from a save game,
     * it can't deserialize the anonymous methods, so we mark them as transient,
     * then manually assign them using this method, which gets called automagically
     * by the XStream serializer.
     */
    override fun readResolve(): Any {
        val newInstance = this::class.java.newInstance()
        shouldShowEvent = newInstance.shouldShowEvent
        interactionPrompt = newInstance.interactionPrompt
        textToStartInteraction = newInstance.textToStartInteraction
        return super.readResolve()
    }

    fun buildBarEvent(): BarEvent {
        return object : BarEvent() {
            init {
                navigator = object : InteractionDefinition<S>.PageNavigator() {

                    override fun close(doNotOfferAgain: Boolean) {
                        if (doNotOfferAgain) {
                            BarEventManager.getInstance().notifyWasInteractedWith(event)
                        }

                        done = true
                        noContinue = true
                    }
                }
            }

            override fun shouldShowAtMarket(market: MarketAPI?): Boolean =
                super.shouldShowAtMarket(market) && market?.let(shouldShowEvent) ?: true

            /**
             * Set up the text that appears when the player goes to the bar
             * and the option for them to init the conversation.
             */
            override fun addPromptAndOption(dialog: InteractionDialogAPI) {
                super.addPromptAndOption(dialog)
                regen(dialog.interactionTarget.market)
                this@BarEventDefinition.manOrWoman = manOrWoman
                this@BarEventDefinition.hisOrHer = hisOrHer
                this@BarEventDefinition.heOrShe = heOrShe
                this@BarEventDefinition.dialog = dialog
                this@BarEventDefinition.event = this
                interactionPrompt(this@BarEventDefinition as S)

                dialog.optionPanel.addOption(
                    textToStartInteraction(),
                    this as BaseBarEventWithPerson
                )
            }

            /**
             * Called when the player chooses to start the conversation.
             */
            override fun init(dialog: InteractionDialogAPI) {
                super.init(dialog)

                if (this@BarEventDefinition.personName != null) {
                    this.person.apply { name = this@BarEventDefinition.personName }
                }

                this.done = false
                this.noContinue = false
                dialog.visualPanel.showPersonInfo(this.person, true)
                onInteractionStarted(this@BarEventDefinition as S)

                if (pages.any()) {
                    showPage(pages.first())
                }
            }

            override fun optionSelected(optionText: String?, optionData: Any?) {
                navigator.onOptionSelected(optionText, optionData)
            }

            fun showPage(page: Page<S>) {
                if (noContinue || done) return

                navigator.showPage(page)
            }

            override fun getPersonFaction(): String? = this@BarEventDefinition.personFaction
                ?: super.getPersonFaction()

            override fun getPersonGender(): FullName.Gender? = this@BarEventDefinition.personName?.gender
                ?: super.getPersonGender()

            override fun getPersonPortrait(): String? = this@BarEventDefinition.personPortrait
                ?: super.getPersonPortrait()

            override fun getPersonPost(): String? = this@BarEventDefinition.personPost
                ?: super.getPersonPost()

            override fun getPersonRank(): String? = this@BarEventDefinition.personRank
                ?: this@BarEventDefinition.personPost
                ?: super.getPersonRank()
        }
    }
}