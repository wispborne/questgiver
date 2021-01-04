package wisp.questgiver.wispLib

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
     */
    fun onGameLoad() {
        game = ServiceLocator()
    }

    /**
     * The mod prefix, without a trailing underscore.
     */
    lateinit var MOD_PREFIX: String
    var isDebugModeEnabled: Boolean = false


    /**
     * Singleton instance of the service locator. Set a new one of these for unit tests.
     */
    internal var game: ServiceLocator = ServiceLocator()
}