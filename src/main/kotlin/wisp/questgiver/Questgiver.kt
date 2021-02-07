package wisp.questgiver

import com.fs.starfarer.api.campaign.SectorEntityToken
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import wisp.questgiver.wispLib.ServiceLocator
import wisp.questgiver.wispLib.Text

object Questgiver {
    internal var blacklistedEntityTags: List<String> = emptyList()

    /**
     * An idempotent method to initialize Questgiver with enough information to start up.
     *
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
     * @param blacklistedEntityTags If a [SectorEntityToken] or any of its parents (eg connectedEntities or star system) use a tag
     *   from this list, do not use it as a quest target.
     */
    fun onGameLoad(
        questFacilitators: List<QuestFacilitator>,
        text: Text,
        blacklistedEntityTags: List<String>
    ) {
        game = ServiceLocator()

        this.blacklistedEntityTags = blacklistedEntityTags

        MainThreadExecutor.start()

        questFacilitators.forEach { questFacilitator ->
            if (questFacilitator is AutoQuestFacilitator) {
                questFacilitator.onDestroy()
                questFacilitator.onGameLoad()
                questFacilitator.autoBarEvent
                    ?.also {
                        BarEventManager.getInstance()
                            .applyBarEventCreatorBasedOnQuestStage(it.barEventCreator, questFacilitator.stage)
                    }
            }

            questFacilitator.updateTextReplacements(text)
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