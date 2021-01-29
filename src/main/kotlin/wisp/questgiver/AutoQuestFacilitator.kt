package wisp.questgiver

import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventCreator
import wisp.questgiver.wispLib.*
import kotlin.random.Random

abstract class AutoQuestFacilitator : QuestFacilitator() {

    abstract val managedLifecycle: ManagedLifecycle<*>

    private var observerId = Random.nextInt()

    class ManagedLifecycle<S : Stage>(
        val stage: PersistentObservableData<S>,
        internal val autoManagedIntelInfo: IntelInformation?

    )

    internal fun onGameLoad() {
        observerId = Random.nextInt()

        if (managedLifecycle.autoManagedIntelInfo != null) {
            managedLifecycle.stage.observers[observerId] = { newStage: Stage ->
                val newlyCreatedIntel = managedLifecycle.autoManagedIntelInfo.intelCreator()
                val intel = Questgiver.game.intelManager.findFirst(newlyCreatedIntel::class.java)

                if (newStage !is Stage.NotStarted
                    && !newStage.isQuestComplete
                    && intel == null
                ) {
                    Questgiver.game.intelManager.addIntel(newlyCreatedIntel)
                } else if (
                    (newStage.isQuestComplete
                            && intel != null)
                    && !intel.isEnding
                    && !intel.isEnded
                ) {
                    intel.endAndNotifyPlayer()
                }
            }
        }
    }

    internal fun onDestroy() {
        managedLifecycle.stage.observers.remove(observerId)
    }

    abstract class Stage(val isQuestComplete: Boolean) {
        open class NotStarted : Stage(isQuestComplete = false)
        open class Complete : Stage(isQuestComplete = true)
    }

    open class IntelInformation(
        val intelCreator: () -> BaseIntelPlugin
    )

    /**
     * If this quest is found at a bar, return a [BarEventInformation] object.
     * The [BaseBarEventCreator] will automatically be added to [BarEventManager].
     * Return `null` if the quest is not found at a bar, or to manage this by yourself.
     */
    open inner class BarEventInformation(
        val barEventCreator: BaseBarEventCreator,
        private val shouldOfferFromMarket: (MarketAPI) -> Boolean
    ) {
        /**
         * Whether the quest should be offered at the [MarketAPI] of the specified bar.
         * Only override for very custom logic, such as if the quest should still be offered even if it is complete.
         */
        open fun shouldOfferFromMarketInternal(market: MarketAPI): Boolean =
            !this@AutoQuestFacilitator.isComplete()
                    && !market.isBlacklisted
                    && shouldOfferFromMarket(market)
    }
}