package wisp.questgiver.v2

import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventCreator
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithBarEvent
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers
import wisp.questgiver.QuestFacilitator
import wisp.questgiver.Questgiver.game
import wisp.questgiver.configureBarEventCreator
import wisp.questgiver.wispLib.Text
import wisp.questgiver.wispLib.removeBarEventCreator
import wisp.questgiver.wispLib.trigger

interface IQGHubMission : QuestFacilitator {
    override fun updateTextReplacements(text: Text)

    fun create(createdAt: MarketAPI?, barEvent: Boolean): Boolean {
        updateTextReplacements(game.text)
        return true
    }
}

abstract class QGHubMission : HubMissionWithTriggers(), IQGHubMission
abstract class QGHubMissionWithBarEvent(
    internal val barEventCreator: BaseBarEventCreator
) : HubMissionWithBarEvent(), IQGHubMission {
    abstract override fun shouldShowAtMarket(market: MarketAPI?): Boolean

    init {
        trigger {
            this.beginStageTrigger(*stages.keys.toTypedArray())
            this.triggerCustomAction {
                onStageChanged()
            }
        }
    }

    private fun onStageChanged() {
        //            if (oldStage == newStage)
        //                return@observable
        val isStarted = this.currentStage != this.startingStage
        //        val isStarted = newStage.progress != AutoQuestFacilitator.Stage.Progress.NotStarted

        // Don't need to manage intel now, HubMission does it.
        //        if (autoIntelInfo != null) {
        //            val shownIntel = Questgiver.game.intelManager.findFirst(autoIntelInfo.intelClass)
        //
        //            if (newStage.progress == AutoQuestFacilitator.Stage.Progress.NotStarted && shownIntel != null) {
        //                shownIntel.endImmediately()
        //                Questgiver.game.intelManager.removeIntel(shownIntel)
        //            } else if (newStage.progress == AutoQuestFacilitator.Stage.Progress.InProgress && shownIntel == null) {
        //                Questgiver.game.intelManager.addIntel(autoIntelInfo.intelCreator())
        //            } else if (
        //                (newStage.progress == AutoQuestFacilitator.Stage.Progress.Completed && shownIntel != null)
        //                && !shownIntel.isEnding
        //                && !shownIntel.isEnded
        //            ) {
        //                shownIntel.endAndNotifyPlayer()
        //            }
        //        }

        val barEventManager = BarEventManager.getInstance()

        // If we just moved to NotStarted from a different stage, reset the timer so it's immediately available
        if (!isStarted && barEventManager.hasEventCreator(barEventCreator::class.java)
        ) {
            barEventManager.setTimeout(barEventCreator::class.java, 0f)
            barEventManager.removeBarEventCreator(barEventCreator::class.java)
        }

        barEventManager
            .configureBarEventCreator(
                shouldGenerateBarEvent = true,
                barEventCreator = barEventCreator,
                isStarted = isStarted
            )
    }

}