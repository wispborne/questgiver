package wisp.questgiver.v2

import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.missions.hub.BaseHubMission
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithBarEvent
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers
import wisp.questgiver.QuestFacilitator
import wisp.questgiver.Questgiver.game
import wisp.questgiver.wispLib.Text

interface IQGHubMission : QuestFacilitator {
    /**
     * Instance of this class case to [BaseHubMission] as a utility to filter to only public methods.
     */
    val thisExt: BaseHubMission

    override fun updateTextReplacements(text: Text)

    fun create(createdAt: MarketAPI?, barEvent: Boolean): Boolean {
        updateTextReplacements(game.text)
        return true
    }
}

abstract class QGHubMission : HubMissionWithTriggers(), IQGHubMission {
    override val thisExt: BaseHubMission
        get() = this
}

abstract class QGHubMissionWithBarEvent() : HubMissionWithBarEvent(), IQGHubMission {
    override val thisExt: BaseHubMission
        get() = this

    abstract override fun shouldShowAtMarket(market: MarketAPI?): Boolean
}