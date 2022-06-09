package wisp.questgiver.v2

import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers
import wisp.questgiver.Questgiver.game
import wisp.questgiver.wispLib.Text

abstract class QGHubMission : HubMissionWithTriggers() {
    abstract fun updateTextReplacements(text: Text)

    override fun create(createdAt: MarketAPI?, barEvent: Boolean): Boolean {
        updateTextReplacements(game.text)
        return true
    }
}