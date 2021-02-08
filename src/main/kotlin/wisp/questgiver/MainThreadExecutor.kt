package wisp.questgiver

import com.fs.starfarer.api.EveryFrameScript
import wisp.questgiver.Questgiver.game
import java.util.concurrent.ConcurrentLinkedQueue

object MainThreadExecutor {
    /**
     * https://stackoverflow.com/a/52973426/1622788
     */
    private val methodsToExecuteOnAdvance = ConcurrentLinkedQueue<(Float) -> Unit>()

    fun post(methodToCall: (secondsSinceLastFrame: Float) -> Unit) {
        methodsToExecuteOnAdvance.add(methodToCall)
    }

    fun start() {
        if (game.sector.transientScripts.none { it is Script }) {
            game.sector.addTransientScript(Script())
        }
    }

    fun stop() {
        if (game.sector.transientScripts.any { it is Script }) {
            game.sector.removeTransientScriptsOfClass(Script::class.java)
        }
    }

    internal class Script : EveryFrameScript {
        override fun isDone(): Boolean = false

        override fun runWhilePaused(): Boolean = true

        /**
         * Called every frame.
         * @param time seconds elapsed during the last frame.
         */
        override fun advance(time: Float) {
            methodsToExecuteOnAdvance.forEach { it(time) }
            methodsToExecuteOnAdvance.clear()
        }
    }
}