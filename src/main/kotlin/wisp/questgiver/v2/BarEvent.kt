package wisp.questgiver.v2

import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.campaign.rules.MemoryAPI
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEvent
import wisp.questgiver.isValidQuestTarget

/**
 * Custom Questgiver bar event, subclass of [BaseBarEvent]. Implement this.
 */
abstract class BarEvent<S : IInteractionLogic<S>>(barEventSpecId: String) :
    HubMissionBarEventWrapperWithoutRules(barEventSpecId) {
    abstract fun createBarEventLogic(): BarEventLogic<S>

    @Transient
    private var definition: BarEventLogic<S> = this.createBarEventLogic()

    override fun readResolve(): Any {
        @Suppress("SENSELESS_COMPARISON")
        if (definition == null) {
            definition = createBarEventLogic()
        }

        return super.readResolve()
    }

    override fun shouldShowAtMarket(market: MarketAPI?): Boolean =
        super.shouldShowAtMarket(market)
                && (market?.isValidQuestTarget ?: true)
                && mission?.result == null

    /**
     * Set up the text that appears when the player goes to the bar
     * and the option for them to init the conversation.
     */
    override fun addPromptAndOption(dialog: InteractionDialogAPI, memoryMap: MutableMap<String, MemoryAPI?>) {
        super.addPromptAndOption(dialog, memoryMap)
//            definition.manOrWoman = manOrWoman
//            definition.hisOrHer = hisOrHer
//            definition.heOrShe = heOrShe
        definition.dialog = dialog
        definition.event = this
        definition.createInteractionPrompt.invoke(definition as S)

        dialog.optionPanel.addOption(
            definition.textToStartInteraction.invoke(definition as S),
            this as BaseBarEvent
        )
    }

    /**
     * Called when the player chooses to start the conversation.
     */
    override fun init(dialog: InteractionDialogAPI, memoryMap: MutableMap<String, MemoryAPI>) {
        super.init(dialog, memoryMap)
        val firstPerson = definition.people?.firstOrNull()

//            if (firstPerson?.name != null) {
//                this.person.apply { name = firstPerson.name }
//            }


        // Set bar event close logic.
        definition.closeBarEvent = { doNotOfferAgain ->
            if (doNotOfferAgain) {
                BarEventManager.getInstance().notifyWasInteractedWith(this)
            }

            done = true
            noContinue = true
        }

        this.done = false
        this.noContinue = false

        definition.onInteractionStarted.invoke(definition as S)

        if (definition.pages.any()) {
            showPage(definition.pages.first())
        }
    }

    override fun optionSelected(optionText: String?, optionData: Any?) {
        definition.navigator.onOptionSelected(optionText, optionData)
    }

    fun showPage(page: IInteractionLogic.Page<S>) {
        if (noContinue || done) return

        definition.navigator.showPage(page)
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