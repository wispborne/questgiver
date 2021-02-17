package wisp.questgiver.wispLib

import org.apache.log4j.Level
import org.apache.log4j.Logger
import wisp.questgiver.Questgiver.game

typealias DebugLogger = Logger

fun DebugLogger.w(ex: Throwable? = null, message: () -> String) {
    if (game.logger.level <= Level.WARN) {
        if (ex != null) {
            this.warn(message(), ex)
        } else {
            this.warn(message())
        }
    }
}

fun DebugLogger.i(ex: Throwable? = null, message: () -> String) {
    if (game.logger.level <= Level.INFO) {
        if (ex != null) {
            this.info(message(), ex)
        } else {
            this.info(message())
        }
    }
}

fun DebugLogger.d(ex: Throwable? = null, message: () -> String) {
    if (game.logger.level <= Level.DEBUG) {
        if (ex != null) {
            this.debug(message(), ex)
        } else {
            this.debug(message())
        }
    }
}