package wisp.questgiver

import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.characters.FullName

abstract class AutoBarEventDefinition<S : InteractionDefinition<S>>(
    @Transient private var questFacilitator: AutoQuestFacilitator,
    createInteractionPrompt: S.() -> Unit,
    textToStartInteraction: S.() -> String,
    onInteractionStarted: S.() -> Unit,
    pages: List<InteractionDefinition.Page<S>>,
    personRank: String? = null,
    personFaction: String? = null,
    personPost: String? = null,
    personPortrait: String? = null,
    personName: FullName? = null
) : BarEventDefinition<S>(
    shouldShowAtMarket = { questFacilitator.autoBarEvent?.shouldOfferFromMarketInternal(it) ?: true },
    interactionPrompt = {},
    createInteractionPrompt = createInteractionPrompt,
    textToStartInteraction = textToStartInteraction,
    onInteractionStarted = onInteractionStarted,
    pages = pages,
    personRank = personRank,
    personFaction = personFaction,
    personPost = personPost,
    personPortrait = personPortrait,
    personName = personName
) {


    /**
     * When this class is created by deserializing from a save game,
     * it can't deserialize the anonymous methods, so we mark them as transient,
     * then manually assign them using this method, which gets called automagically
     * by the XStream serializer.
     */
    override fun readResolve(): Any {
        val newInstance = this::class.java.newInstance()
        questFacilitator = newInstance.questFacilitator
        return super.readResolve()
    }

    override fun buildBarEvent(): BarEvent = AutoBarEvent()

    open inner class AutoBarEvent : BarEvent() {
        override fun addPromptAndOption(dialog: InteractionDialogAPI) {
            questFacilitator.regenerateQuest(dialog.interactionTarget, dialog.interactionTarget.market)
            super.addPromptAndOption(dialog)
        }
    }
}