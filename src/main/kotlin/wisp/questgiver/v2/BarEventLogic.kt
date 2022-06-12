package wisp.questgiver.v2

import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.characters.PersonAPI
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEvent
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventWithPerson
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithBarEvent
import wisp.questgiver.OnInteractionStarted

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
open class BarEventLogic<H: HubMissionWithBarEvent>(
    @Transient internal var createInteractionPrompt: CreateInteractionPrompt<BarEventLogic<H>>,
    @Transient internal var textToStartInteraction: TextToStartInteraction<BarEventLogic<H>>,
    override var onInteractionStarted: OnInteractionStarted<BarEventLogic<H>>,
    override var pages: List<IInteractionLogic.Page<BarEventLogic<H>>>,
    override var people: List<PersonAPI>? = null,
) : IInteractionLogic<BarEventLogic<H>>//(
//    onInteractionStarted = onInteractionStarted,
//    people = people,
//    pages = pages
//)
{

//    override var onInteractionStarted: wisp.questgiver.v2.OnInteractionStarted<S>
//        get() = this.onInteractionStarted
//        set(value) {}

    override lateinit var dialog: InteractionDialogAPI
//        get() = this.dialog

    internal lateinit var missionGetter: () -> H
    val mission: H
        get() = missionGetter.invoke()

    //    lateinit var manOrWoman: String
//    lateinit var hisOrHer: String
//    lateinit var heOrShe: String
    lateinit var event: BaseBarEvent

    internal lateinit var closeBarEvent: (doNotOfferAgain: Boolean) -> Unit
//    val interactionDefinition

//    /**
//     * UPDATE: shouldn't need this, no longer serializing this class.
//     * ---
//     * When this class is created by deserializing from a save game,
//     * it can't deserialize the anonymous methods, so we mark them as transient,
//     * then manually assign them using this method, which gets called automagically
//     * by the XStream serializer.
//     */
//    fun readResolve(): Any {
//        val newInstance = this::class.java.newInstance()
//        createInteractionPrompt = newInstance.createInteractionPrompt
//        textToStartInteraction = newInstance.textToStartInteraction
//        return this
//    }

    final override var navigator = object : InteractionLogic.PageNavigator<BarEventLogic<H>>(this) {
        override fun close(doNotOfferAgain: Boolean) {
            closeBarEvent.invoke(doNotOfferAgain)
        }
    }
        internal set
}

