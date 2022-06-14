package wisp.questgiver.v2

import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.campaign.rules.MemoryAPI
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEvent
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithBarEvent
import wisp.questgiver.isValidQuestTarget

/**
 * Custom Questgiver bar event, subclass of [BaseBarEvent]. Implement this.
 */
abstract class BarEvent<H : HubMissionWithBarEvent>(barEventSpecId: String) :
    HubMissionBarEventWrapperWithoutRules<H>(barEventSpecId) {
    abstract fun createBarEventLogic(): BarEventLogic<H>

    @Transient
    private lateinit var barEventLogic: BarEventLogic<H>

    init {
        barEventLogic = setupBarEventLogic()
    }

    override fun readResolve(): Any {
        @Suppress("SENSELESS_COMPARISON")
        if (barEventLogic == null) {
            barEventLogic = setupBarEventLogic()
        }

        return super.readResolve()
    }

    private fun setupBarEventLogic(): BarEventLogic<H> {
        return createBarEventLogic().also { logic ->
            logic.missionGetter = { this.mission!! }
        }
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
        barEventLogic.dialog = dialog
        barEventLogic.event = this
        barEventLogic.createInteractionPrompt.invoke(barEventLogic)

        dialog.optionPanel.addOption(
            barEventLogic.textToStartInteraction.invoke(barEventLogic),
            this as BaseBarEvent
        )
    }

    /**
     * Called when the player chooses to start the conversation.
     */
    override fun init(dialog: InteractionDialogAPI, memoryMap: MutableMap<String, MemoryAPI>) {
        super.init(dialog, memoryMap)

//            if (firstPerson?.name != null) {
//                this.person.apply { name = firstPerson.name }
//            }
        val people = barEventLogic.people?.invoke(barEventLogic)

        people?.forEachIndexed { index, person ->
            when (index) {
                0 -> dialog.visualPanel.showPersonInfo(person)
                1 -> dialog.visualPanel.showSecondPerson(person)
                2 -> dialog.visualPanel.showThirdPerson(person)
            }
        }

        // Set bar event close logic.
        barEventLogic.closeBarEvent = { doNotOfferAgain ->
            if (doNotOfferAgain) {
                BarEventManager.getInstance().notifyWasInteractedWith(this)
            }

            done = true
            noContinue = true
        }

        this.done = false
        this.noContinue = false

        barEventLogic.onInteractionStarted.invoke(barEventLogic)

        if (barEventLogic.pages.any()) {
            showPage(barEventLogic.pages.first())
        }
    }

    override fun optionSelected(optionText: String?, optionData: Any?) {
        barEventLogic.navigator.onOptionSelected(optionText, optionData)
    }

    fun showPage(page: IInteractionLogic.Page<BarEventLogic<H>>) {
        if (noContinue || done) return

        barEventLogic.navigator.showPage(page)
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