package wisp.questgiver.v2

import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.campaign.rules.MemoryAPI
import com.fs.starfarer.api.characters.PersonAPI
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventWithPerson
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionBarEventWrapper
import com.fs.starfarer.api.ui.LabelAPI
import wisp.questgiver.OnInteractionStarted
import wisp.questgiver.ParagraphText
import wisp.questgiver.addPara
import wisp.questgiver.isValidQuestTarget
import java.awt.Color

typealias CreateInteractionPrompt<S> = S.() -> Unit
typealias TextToStartInteraction<S> = S.() -> String

/**
 * Defines a [BaseBarEventWithPerson]. Create the [BaseBarEventWithPerson] by calling [buildBarEvent].
 * @param specId The mission/bar event id.
 * @param createInteractionPrompt Add text/images to the bar to show that this event is present,
 *   e.g. "A man is searching for something in the corner."
 * @param textToStartInteraction The option available to the player to start the event, e.g. "Help the man."
 * @param onInteractionStarted Called when the player chooses to start the bar event.
 * @param pages A list of [wisp.questgiver.InteractionDefinition.Page]s that define the structure of the conversation.
 */
open class BarEventDefinition<S : IInteractionDefinition<S>>(
    specId: String,
    @Transient internal var createInteractionPrompt: CreateInteractionPrompt<S>,
    @Transient internal var textToStartInteraction: TextToStartInteraction<S>,
    override var onInteractionStarted: OnInteractionStarted<S>,
    override var pages: List<IInteractionDefinition.Page<S>>,
    override var people: List<PersonAPI>? = null,
) : HubMissionBarEventWrapper(specId), IInteractionDefinition<S>//(
//    onInteractionStarted = onInteractionStarted,
//    people = people,
//    pages = pages
//)
{

//    override var onInteractionStarted: wisp.questgiver.v2.OnInteractionStarted<S>
//        get() = this.onInteractionStarted
//        set(value) {}

    override val dialog: InteractionDialogAPI
        get() = this.dialog

    lateinit var manOrWoman: String
    lateinit var hisOrHer: String
    lateinit var heOrShe: String
    lateinit var event: HubMissionBarEventWrapper
//    val interactionDefinition

    /**
     * When this class is created by deserializing from a save game,
     * it can't deserialize the anonymous methods, so we mark them as transient,
     * then manually assign them using this method, which gets called automagically
     * by the XStream serializer.
     */
    override fun readResolve(): Any {
        val newInstance = this::class.java.newInstance()
        createInteractionPrompt = newInstance.createInteractionPrompt
        textToStartInteraction = newInstance.textToStartInteraction
        return super.readResolve()
    }

    final override var navigator = object : wisp.questgiver.v2.InteractionDefinition.PageNavigator<S>(this) {
        override fun close(doNotOfferAgain: Boolean) {
            if (doNotOfferAgain) {
                BarEventManager.getInstance().notifyWasInteractedWith(event)
            }

            done = true
            noContinue = true
        }
    }
        internal set

    override fun shouldShowAtMarket(market: MarketAPI?): Boolean =
        super.shouldShowAtMarket(market)
                && (market?.isValidQuestTarget ?: true)

    /**
     * Set up the text that appears when the player goes to the bar
     * and the option for them to init the conversation.
     */
    override fun addPromptAndOption(dialog: InteractionDialogAPI, memoryMap: MutableMap<String, MemoryAPI>) {
        super.addPromptAndOption(dialog, memoryMap)
        this@BarEventDefinition.manOrWoman = manOrWoman
        this@BarEventDefinition.hisOrHer = hisOrHer
        this@BarEventDefinition.heOrShe = heOrShe
        this@BarEventDefinition.dialog = dialog
        this@BarEventDefinition.event = this
        createInteractionPrompt.invoke(this@BarEventDefinition as S)

        dialog.optionPanel.addOption(
            textToStartInteraction.invoke(this@BarEventDefinition),
            this as BaseBarEventWithPerson
        )
    }

    /**
     * Called when the player chooses to start the conversation.
     */
    override fun init(dialog: InteractionDialogAPI, memoryMap: MutableMap<String, MemoryAPI>) {
        super.init(dialog, memoryMap)
        val firstPerson = this@BarEventDefinition.people?.firstOrNull()

//            if (firstPerson?.name != null) {
//                this.person.apply { name = firstPerson.name }
//            }

        this.done = false
        this.noContinue = false

        onInteractionStarted.invoke(this@BarEventDefinition as S)

        if (pages.any()) {
            showPage(pages.first())
        }
    }

    override fun optionSelected(optionText: String?, optionData: Any?) {
        navigator.onOptionSelected(optionText, optionData)
    }

    fun showPage(page: IInteractionDefinition.Page<S>) {
        if (noContinue || done) return

        navigator.showPage(page)
    }

//    override fun build(): IInteractionDefinition.InteractionDialog = this.createInteractionPrompt.invoke(this)

//        override fun getPersonFaction(): String? = this@BarEventDefinition.people?.firstOrNull()?.faction?.id
//            ?: super.getPersonFaction()
//
//        override fun getPersonGender(): FullName.Gender? = this@BarEventDefinition.people?.firstOrNull()?.gender
//            ?: super.getPersonGender()
//
//        override fun getPersonPortrait(): String? = this@BarEventDefinition.people?.firstOrNull()?.portraitSprite
//            ?: super.getPersonPortrait()
//
//        override fun getPersonPost(): String? = this@BarEventDefinition.people?.firstOrNull()?.postId
//            ?: super.getPersonPost()
//
//        override fun getPersonRank(): String? = this@BarEventDefinition.people?.firstOrNull()?.rankId
//            ?: personPost
//            ?: super.getPersonRank()
}