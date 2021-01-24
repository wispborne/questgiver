package wisp.questgiver

import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventCreator
import wisp.questgiver.wispLib.Text

abstract class QuestFacilitator {

    /**
     * An idempotent method to configure `game.text.globalReplacementGetters`.
     *
     * Usage:
     * ```kt
     * override fun updateTextReplacements(text: Text) {
     *   text.globalReplacementGetters["depthsSourcePlanet"] = { startingPlanet?.name }
     *   text.globalReplacementGetters["depthsDestPlanet"] = { destPlanet?.name }
     * }
     * ```
     */
    abstract fun updateTextReplacements(text: Text)

    /**
     * Returns the quest's [BaseBarEventCreator], if it has one.
     * It will automatically be added to [BarEventManager].
     * Return `null` to manage this by yourself.
     */
    abstract fun getBarEventCreator(): BaseBarEventCreator?

    /**
     * Whether the quest has been started or not.
     */
    abstract fun hasBeenStarted(): Boolean
}