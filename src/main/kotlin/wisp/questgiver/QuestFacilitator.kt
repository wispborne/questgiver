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
}