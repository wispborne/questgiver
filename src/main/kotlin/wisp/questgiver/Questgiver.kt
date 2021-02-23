package wisp.questgiver

import com.fs.starfarer.api.campaign.SectorEntityToken
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import wisp.questgiver.wispLib.QuestgiverServiceLocator
import wisp.questgiver.wispLib.ServiceLocator
import wisp.questgiver.wispLib.Text

object Questgiver {
    internal lateinit var questFacilitators: List<QuestFacilitator>

    /**
     * An idempotent method to initialize Questgiver with enough information to start up.
     *
     * @param modPrefix The mod prefix to use, without a trailing underscore.
     */
    fun init(modPrefix: String) {
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
        configuration: Configuration
    ) {
        this.questFacilitators = questFacilitators

        game.configuration = configuration

        questFacilitators.forEach { questFacilitator ->
            if (questFacilitator is AutoQuestFacilitator) {
                questFacilitator.onDestroy()
                questFacilitator.onGameLoad()
                questFacilitator.autoBarEventInfo
                    ?.also {
                        BarEventManager.getInstance()
                            .configureBarEventCreator(it, questFacilitator.stage)
                    }
            }

            questFacilitator.updateTextReplacements(game.text)
        }

        QuestgiverEveryFrameScript.start()
    }

    /**
     * The mod prefix, without a trailing underscore.
     */
    internal lateinit var MOD_PREFIX: String


    /**
     * Singleton instance of the service locator. Set a new one of these for unit tests.
     */
    var game: ServiceLocator = QuestgiverServiceLocator()
        internal set
}