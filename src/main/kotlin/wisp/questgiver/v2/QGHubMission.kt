package wisp.questgiver.v2

import com.fs.starfarer.api.PluginPick
import com.fs.starfarer.api.campaign.BaseCampaignPlugin
import com.fs.starfarer.api.campaign.InteractionDialogPlugin
import com.fs.starfarer.api.campaign.SectorEntityToken
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithBarEvent
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers
import wisp.questgiver.QuestFacilitator
import wisp.questgiver.Questgiver.game
import wisp.questgiver.wispLib.Text
import kotlin.random.Random

interface IQGHubMission : QuestFacilitator {
    override fun updateTextReplacements(text: Text)

    fun create(createdAt: MarketAPI?, barEvent: Boolean): Boolean {
        updateTextReplacements(game.text)
        return true
    }

    /**
     * Handle interactions or return null to ignore.
     * HubMission already has tons of methods and overrides, what's one more?
     */
//    fun pickInteractionDialogPlugin(interactionTarget: SectorEntityToken): PluginPick<InteractionDialogPlugin>? {
//        return null
//    }
}

abstract class QGHubMission : HubMissionWithTriggers(), IQGHubMission {
//
//    init {
//        registerPlugin()
//    }
//
//    open fun readResolve(): Any {
//        registerPlugin()
//        return this
//    }
//
//    private fun registerPlugin() {
//        game.sector.registerPlugin(object : BaseCampaignPlugin() {
//            private val id = "Questgiver_Wisp_CampaignPlugin_${Random.nextInt()}"
//
//            override fun getId(): String {
//                return id
//            }
//
//            // No need to add to saves
//            override fun isTransient(): Boolean = true
////
////            /**
////             * When the player interacts with a dialog, override the default interaction with a
////             * mod-specific one if necessary.
////             */
////            override fun pickInteractionDialogPlugin(interactionTarget: SectorEntityToken): PluginPick<InteractionDialogPlugin>? {
////                return this@QGHubMission.pickInteractionDialogPlugin(interactionTarget)
////            }
//        })
//    }
}

abstract class QGHubMissionWithBarEvent() : HubMissionWithBarEvent(), IQGHubMission  {
    abstract override fun shouldShowAtMarket(market: MarketAPI?): Boolean
//    init {
//        registerPlugin()
//    }
//
//    open fun readResolve(): Any {
//        registerPlugin()
//        return this
//    }
//
//    private fun registerPlugin() {
//        game.sector.registerPlugin(object : BaseCampaignPlugin() {
//            private val id = "Questgiver_Wisp_CampaignPlugin_${Random.nextInt()}"
//
//            override fun getId(): String {
//                return id
//            }
//
//            // No need to add to saves
//            override fun isTransient(): Boolean = true
//
//            /**
//             * When the player interacts with a dialog, override the default interaction with a
//             * mod-specific one if necessary.
//             */
//            override fun pickInteractionDialogPlugin(interactionTarget: SectorEntityToken): PluginPick<InteractionDialogPlugin>? {
//                return this@QGHubMissionWithBarEvent.pickInteractionDialogPlugin(interactionTarget)
//            }
//        })
//    }
}