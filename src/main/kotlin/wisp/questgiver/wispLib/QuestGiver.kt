package wisp.questgiver.wispLib

import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import wisp.questgiver.QuestFacilitator

object QuestGiver {

    /**
     * @param modPrefix The mod prefix to use, without a trailing underscore.
     */
    fun initialize(
        modPrefix: String
    ) {
        MOD_PREFIX = modPrefix
    }

    /**
     * Call this when a save game is loaded.
     * This refreshes the sector data so that the loaded game doesn't have any data from the previous save.
     *
     * @param questFacilitators All [QuestFacilitator]s used by the mod.
     * @param text The [Text] used by the mod.
     */
    fun onGameLoad(
        questFacilitators: List<QuestFacilitator>,
        text: Text
    ) {
        game = ServiceLocator()
        questFacilitators.forEach { questFacilitator ->
            questFacilitator.updateTextReplacements(text)

            questFacilitator.getBarEventCreator()
                ?.also {
                    BarEventManager.getInstance().addBarEventCreatorIf(it) {
                        !questFacilitator.hasBeenStarted()
                    }
                }
        }
    }

    /**
     * The mod prefix, without a trailing underscore.
     */
    internal lateinit var MOD_PREFIX: String
    var isDebugModeEnabled: Boolean = false


    /**
     * Singleton instance of the service locator. Set a new one of these for unit tests.
     */
    internal var game: ServiceLocator = ServiceLocator()
}