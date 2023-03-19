package wisp.questgiver.v2

import com.fs.starfarer.api.impl.campaign.FleetInteractionDialogPluginImpl

/**
 * Allows custom dialog options to be added to the vanilla fleet interaction dialog.
 * Usage: `Telos2PirateFleetInteractionDialogPluginImpl` in Persean Chronicles.
 */
abstract class CustomFleetInteractionDialogPlugin<S : InteractionDialogLogic<S>> : FleetInteractionDialogPluginImpl() {
    private val dialogLogic by lazy { createCustomDialogLogic() }
    private val dialogPlugin by lazy { dialogLogic.build() }

    abstract fun createCustomDialogLogic(): InteractionDialogLogic<S>

    override fun optionSelected(text: String?, optionData: Any?) {
        dialogLogic.dialog = this.dialog

        when (optionData) {
            OptionId.OPEN_COMM -> {
                // From vanilla `optionSelected`
                if (text != null) {
                    //textPanel.addParagraph(text, Global.getSettings().getColor("buttonText"));
                    dialog.addOptionSelectedText(optionData)
                }

                // Wisp custom
                dialogPlugin.init(dialog)
            }

            else -> {
                // If the custom pirate comms dialog logic contains the option that was just selected, use that dialog.
                // Otherwise, forward it to the vanilla dialog.
                val doesCommsDialogHandleOption = dialogLogic.pages.flatMap { it.options }
                    .any { it.id == optionData }

                if (doesCommsDialogHandleOption) {
                    dialogPlugin.optionSelected(text, optionData)
                } else {
                    super.optionSelected(text, optionData)
                }
            }
        }

    }
}