package wisp.questgiver.wispLib

import org.apache.log4j.Level
import org.apache.log4j.Logger
import wisp.questgiver.Questgiver
import wisp.questgiver.Questgiver.game

typealias DebugLogger = Logger

fun DebugLogger.w(message: () -> String) {
    if (game.logger.level <= Level.WARN) {
        this.warn(message())
    }
}

fun DebugLogger.i(message: () -> String) {
    if (game.logger.level <= Level.INFO) {
        this.info(message())
    }
}

fun DebugLogger.d(message: () -> String) {
    if (game.logger.level <= Level.DEBUG) {
        this.debug(message())
    }
}