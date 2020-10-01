package wisp.questgiver.wispLib

import org.apache.log4j.Logger
import wisp.questgiver.wispLib.QuestGiver.isDebugModeEnabled

typealias DebugLogger = Logger

fun DebugLogger.w(message: () -> String) {
    this.warn(message())
}

fun DebugLogger.i(message: () -> String) {
    if (isDebugModeEnabled) {
        this.info(message())
    }
}