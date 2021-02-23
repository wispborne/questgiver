package wisp.questgiver.wispLib

import org.apache.log4j.Level
import org.apache.log4j.Logger
import wisp.questgiver.Questgiver.game

class DebugLogger {
    var level = Level.ALL

    @Suppress("NOTHING_TO_INLINE")
    private inline fun getLogger(): Logger {
        val callingClassName = runCatching {
            Thread.currentThread().stackTrace[3].className
        }
            .onFailure { }
            .getOrNull()

        return Logger.getLogger(callingClassName)
    }

    fun w(ex: Throwable? = null, message: (() -> String?)? = null) {
        if (game.logger.level <= Level.WARN) {
            if (ex != null) {
                getLogger().warn(message?.invoke(), ex)
            } else {
                getLogger().warn(message?.invoke())
            }
        }
    }

    fun i(ex: Throwable? = null, message: (() -> String?)? = null) {
        if (game.logger.level <= Level.INFO) {
            if (ex != null) {
                getLogger().info(message?.invoke(), ex)
            } else {
                getLogger().info(message?.invoke())
            }
        }
    }

    fun d(ex: Throwable? = null, message: (() -> String?)? = null) {
        if (game.logger.level <= Level.DEBUG) {
            if (ex != null) {
                getLogger().debug(message?.invoke(), ex)
            } else {
                getLogger().debug(message?.invoke())
            }
        }
    }

    fun e(ex: Throwable? = null, message: (() -> String?)? = null) {
        if (game.logger.level <= Level.ERROR) {
            if (ex != null) {
                getLogger().error(message?.invoke(), ex)
            } else {
                getLogger().error(message?.invoke())
            }
        }
    }
}