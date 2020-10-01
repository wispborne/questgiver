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
     * The mod prefix, without a trailing underscore.
     */
    lateinit var MOD_PREFIX: String
    var isDebugModeEnabled: Boolean = false
}