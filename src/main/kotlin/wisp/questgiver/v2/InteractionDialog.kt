package wisp.questgiver.v2

import com.fs.starfarer.api.campaign.InteractionDialogAPI
import com.fs.starfarer.api.campaign.InteractionDialogPlugin
import com.fs.starfarer.api.campaign.rules.MemoryAPI
import com.fs.starfarer.api.combat.EngagementResultAPI
import wisp.questgiver.Questgiver.game

abstract class InteractionDialog<S : InteractionDialogLogic<S>> : InteractionDialogPlugin {
    abstract fun createInteractionDialogLogic(): S

    @Transient
    private var logic: S = setupInteractionDialogLogic()

    private fun setupInteractionDialogLogic(): S = createInteractionDialogLogic()

    /**
     * Called when the dialog is shown.
     */
    override fun init(dialog: InteractionDialogAPI) {
        logic.dialog = dialog
        val peopleInner = logic.people?.invoke(logic)

        if (peopleInner?.getOrNull(0) != null) {
            dialog.visualPanel.showPersonInfo(peopleInner[0], true)
        }

        if (peopleInner?.getOrNull(1) != null) {
            dialog.visualPanel.showSecondPerson(peopleInner[1])
        }

        if (peopleInner?.getOrNull(2) != null) {
            dialog.visualPanel.showThirdPerson(peopleInner[2])
        }

        logic.onInteractionStarted(logic)

        if (logic.pages.any()) {
            logic.navigator.showPage(logic.pages.first())
        }
    }

    override fun optionSelected(optionText: String?, optionData: Any?) {
        if (optionText != null) {
            // Print out the text of the option the user just selected
            // This was replaced in vanilla with addOptionSelectedText
//            logic.para(textColor = Global.getSettings().getColor("buttonText")) { optionText }
//            logic.dialog.addOptionSelectedText(optionData)

            // Print out the text of the option the user just selected
            val textColor = logic.pages.flatMap { it.options }
                .singleOrNull { it.id == optionData }
                ?.textColor
                ?: game.settings.getColor("buttonText")
            logic.para(textColor = textColor) { optionText }
        }

        logic.navigator.onOptionSelected(optionText, optionData)
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