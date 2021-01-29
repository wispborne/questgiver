package wisp.questgiver

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
     * If this quest has one piece of intel, return it.
     * It will be automatically started and ended.
     * Return `null` if there is no intel or for custom handling.
     */
//    abstract fun createIntel(): BaseIntelPlugin?

    /**
     * Whether the quest has been started or not.
     */
    abstract fun hasBeenStarted(): Boolean

    /**
     * Whether the quest is complete or not.
     * Failure counts as being complete.
     */
    abstract fun isComplete(): Boolean
}