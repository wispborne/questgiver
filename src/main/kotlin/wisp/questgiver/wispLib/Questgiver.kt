package wisp.questgiver.wispLib

import com.fs.starfarer.api.campaign.SectorAPI
import com.fs.starfarer.api.campaign.SectorEntityToken
import com.fs.starfarer.api.campaign.StarSystemAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import wisp.questgiver.QuestFacilitator

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

        questFacilitators.forEach { questFacilitator ->
            questFacilitator.onDestroy()
            questFacilitator.onGameLoad()
            questFacilitator.updateTextReplacements(text)

            questFacilitator.getBarEventInformation()
                ?.also {
                    BarEventManager.getInstance().addBarEventCreatorIf(it.barEventCreator) {
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

    val starSystemsNotOnBlacklist: List<StarSystemAPI>
        get() = game.sector.starSystems.filter { !it.isBlacklisted }
}

val MarketAPI.isBlacklisted: Boolean
    get() = this.connectedEntities.none() { it.isBlacklisted }
            && this.starSystem?.isBlacklisted != true
            && this.tags.all { it !in Questgiver.blacklistedEntityTags }

val SectorEntityToken.isBlacklisted: Boolean
    get() = this.starSystem?.isBlacklisted != true
            && this.tags.all { it !in Questgiver.blacklistedEntityTags }

val StarSystemAPI.isBlacklisted: Boolean
    get() = this.tags.all { it !in Questgiver.blacklistedEntityTags }

val SectorAPI.starSystemsNotOnBlacklist: List<StarSystemAPI>
    get() = Questgiver.starSystemsNotOnBlacklist