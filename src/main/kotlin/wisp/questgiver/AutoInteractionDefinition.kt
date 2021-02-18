package wisp.questgiver

import com.fs.starfarer.api.campaign.InteractionDialogAPI

abstract class AutoInteractionDefinition<S : InteractionDefinition<S>>(
    private val questFacilitator: AutoQuestFacilitator,
    onInteractionStarted: S.() -> Unit = {},
    pages: List<InteractionDefinition.Page<S>>,
    shouldValidateOnDialogStart: Boolean = true
) : InteractionDefinition<S>(
    onInteractionStarted,
    pages,
    shouldValidateOnDialogStart
) {
    internal inner class AutoInteractionDialogImpl : InteractionDialogImpl() {
        override fun init(dialog: InteractionDialogAPI) {
            if (questFacilitator.stage.progress == AutoQuestFacilitator.Stage.Progress.NotStarted) {
                questFacilitator.regenerateQuest(dialog.interactionTarget, dialog.interactionTarget.market)
            }

            super.init(dialog)
        }
    }
}