package wisp.questgiver

import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventCreator
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithBarEvent
import wisp.questgiver.v2.BarEvent
import wisp.questgiver.v2.BarEventLogic
import wisp.questgiver.v2.QGHubMissionWithBarEvent


/**
 * Pass to `QuestGiver.loadQuests`.
 * Creates and adds a [BaseBarEventCreator] to launch a bar event for the specified HubMission.
 */
abstract class BarEventWiring<H : QGHubMissionWithBarEvent>(val missionId: String) {
    /**
     * Creates a [BaseBarEventCreator].
     */
    open fun createBarEventCreator(): BarEventManager.GenericBarEventCreator =
        object : BaseBarEventCreator() {
            override fun createBarEvent() = this@BarEventWiring.createBarEvent()

            override fun isPriority(): Boolean {
                return true // todo remove
            }
        }

    /**
     * Creates the bar event (which gets saved).
     */
    open fun createBarEvent(): BarEvent<H> = object : BarEvent<H>(this@BarEventWiring.missionId) {
        override fun createBarEventLogic(): BarEventLogic<H> = this@BarEventWiring.createBarEventLogic()
        override fun createMission(): H = this@BarEventWiring.createMission()
    }

    /**
     * Creates the logic that drives the bar event (not saved).
     */
    abstract fun createBarEventLogic(): BarEventLogic<H>

    /**
     * Creates a [HubMissionWithBarEvent] that's passed into the [BarEventLogic].
     */
    abstract fun createMission(): H

    /**
     * Whether this bar event should be added to the bar event pool or not.
     * True if quest is enabled and never started, false otherwise.
     */
    abstract fun shouldOfferQuest(): Boolean
}