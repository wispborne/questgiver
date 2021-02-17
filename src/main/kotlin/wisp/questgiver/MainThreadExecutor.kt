package wisp.questgiver

import com.fs.starfarer.api.EveryFrameScript
import wisp.questgiver.Questgiver.game
import wisp.questgiver.wispLib.d
import java.util.concurrent.ConcurrentLinkedQueue

@Deprecated("Doesn't work reliably, throws ConcurrentModificationException")
internal object MainThreadExecutor {
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

    @Deprecated("Doesn't work reliably, throws ConcurrentModificationException")
    internal class Script : EveryFrameScript {
        override fun isDone(): Boolean = true

        override fun runWhilePaused(): Boolean = false

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
            methods.forEach { func ->
                // If it fails with ConcurrentModificationException, retry, maybe it'll work next time.
                // This is awful.
                for (i in 0..4) {
                    try {
                        func.invoke(time)
                        return@forEach
                    } catch (e: ConcurrentModificationException) {
                        game.logger.d(e) { "Caught exception, retrying. ${e.message}" }
                    }
                }
            }
        }
    }
}
//wtf
//java.util.ConcurrentModificationException
//at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:859)
//at java.util.ArrayList$Itr.next(ArrayList.java:831)
//at com.fs.starfarer.ui.for.o00000(Unknown Source)
//at com.fs.starfarer.ui.for.recompute(Unknown Source)
//at com.fs.starfarer.ui.for.setSize(Unknown Source)
//at com.fs.starfarer.ui.o00OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO.setSize(Unknown Source)
//at com.fs.starfarer.ui.newui.C.for.while(Unknown Source)
//at com.fs.starfarer.ui.newui.C.addParagraph(Unknown Source)
//at com.fs.starfarer.ui.newui.C.addPara(Unknown Source)
//at com.fs.starfarer.ui.newui.C.addPara(Unknown Source)
//at wisp.questgiver.TextExtensionsKt.addPara(TextExtensions.kt:24)
//at wisp.questgiver.InteractionDefinition$para$2.invoke(InteractionDefinition.kt:316)
//at wisp.questgiver.InteractionDefinition$para$2.invoke(InteractionDefinition.kt:19)
//at wisp.questgiver.MainThreadExecutor$Script.advance(MainThreadExecutor.kt:47)
//at com.fs.starfarer.campaign.CampaignEngine.advance(Unknown Source)
//at com.fs.starfarer.campaign.CampaignState.advance(Unknown Source)
//at com.fs.starfarer.BaseGameState.traverse(Unknown Source)
//at com.fs.state.AppDriver.begin(Unknown Source)
//at com.fs.starfarer.combat.CombatMain.main(Unknown Source)