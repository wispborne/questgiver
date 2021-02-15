package wisp.questgiver

import com.fs.starfarer.api.EveryFrameScript
import wisp.questgiver.Questgiver.game
import java.util.concurrent.ConcurrentLinkedQueue

object MainThreadExecutor {
    /**
     * https://stackoverflow.com/a/52973426/1622788
     */
    private var methodsToExecuteOnAdvance: MutableCollection<(Float) -> Unit> = ConcurrentLinkedQueue<(Float) -> Unit>()

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
            // Keep list of methods to execute
            val methods = methodsToExecuteOnAdvance

            // Create a new list so any executing methods will add to the new list
            methodsToExecuteOnAdvance = ConcurrentLinkedQueue<(Float) -> Unit>()

            // Execute the methods. Any new methods will go onto the new list
            methods.forEach {
                it(time)
            }
        }
    }
}