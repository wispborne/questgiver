package wisp.questgiver

import com.fs.starfarer.api.PluginPick
import com.fs.starfarer.api.campaign.BaseCampaignPlugin
import com.fs.starfarer.api.campaign.InteractionDialogPlugin
import com.fs.starfarer.api.campaign.SectorEntityToken

class CampaignPlugin {
    /**
     * Instead of using `rules.csv`, use this plugin to trigger dialog choices and conversations.
     */
    class CampaignPlugin() : BaseCampaignPlugin() {

        override fun getId() = "Questgiver_Wisp_CampaignPlugin"

        // No need to add to saves
        override fun isTransient(): Boolean = true

        /**
         * When the player interacts with a dialog, override the default interaction with a
         * mod-specific one if necessary.
         */
        override fun pickInteractionDialogPlugin(interactionTarget: SectorEntityToken): PluginPick<InteractionDialogPlugin>? {
            return null
        }
    }
}