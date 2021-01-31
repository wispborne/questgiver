package wisp.questgiver

import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventCreator
import wisp.questgiver.AutoQuestFacilitator.AutoBarEvent
import wisp.questgiver.AutoQuestFacilitator.AutoIntel
import wisp.questgiver.wispLib.*
import kotlin.properties.Delegates

/**
 * @param autoIntel If the quest has intel, use this field to have it managed. See [AutoIntel].
 * @param autoBarEvent If the quest has a bar event, use this field to have it managed. See [AutoBarEvent].
 */
abstract class AutoQuestFacilitator(
    private var stageBackingField: PersistentData<Stage>,
    internal val autoIntel: AutoIntel<out BaseIntelPlugin>?,
    internal val autoBarEvent: AutoBarEvent?
) : QuestFacilitator() {

    var stage: Stage by Delegates.observable(stageBackingField.get()) { _, oldStage, newStage ->
        // Update backing field to update save game
        if (stageBackingField != newStage) {
            stageBackingField.set(newStage)
        }

        if (oldStage == newStage)
            return@observable

        if (autoIntel != null) {
            val shownIntel = Questgiver.game.intelManager.findFirst(autoIntel.intelClass)

            if (newStage.progress == Stage.Progress.NotStarted && shownIntel != null) {
                shownIntel.endImmediately()
            } else if (newStage.progress == Stage.Progress.InProgress && shownIntel == null) {
                Questgiver.game.intelManager.addIntel(autoIntel.intelCreator())
            } else if (
                (newStage.progress == Stage.Progress.Completed && shownIntel != null)
                && !shownIntel.isEnding
                && !shownIntel.isEnded
            ) {
                shownIntel.endAndNotifyPlayer()
            }
        }
    }

    internal fun onGameLoad() {
        stage = stageBackingField.get()
        autoBarEvent?.stage = { stageBackingField.get() }
    }

    /**
     * Avoid any potential memory leaks or things held across saves.
     */
    internal fun onDestroy() {
        autoBarEvent?.stage = null
    }

    /**
     * The current stage of the quest.
     */
    abstract class Stage(val progress: Progress) {
        enum class Progress {
            NotStarted,
            InProgress,
            Completed
        }

        val isCompleted = progress == Progress.Completed
    }

    /**
     * Automatically displays the given [BaseIntelPlugin] when the quest's [Stage.Progress] is in progress.
     * Marks the intel as complete once the quest's progress is complete.
     */
    open class AutoIntel<T : BaseIntelPlugin>(
        val intelClass: Class<T>,
        val intelCreator: () -> T
    )

    /**
     * If this quest is found at a bar, return a [AutoBarEvent] object.
     * The [BaseBarEventCreator] will automatically be added to [BarEventManager].
     * Return `null` if the quest is not found at a bar, or to manage this by yourself.
     */
    open class AutoBarEvent(
        val barEventCreator: BaseBarEventCreator,
        private val shouldOfferFromMarket: (MarketAPI) -> Boolean
    ) {
        internal var stage: (() -> Stage)? = null

        /**
         * Whether the quest should be offered at the [MarketAPI] of the specified bar.
         * Only override for very custom logic, such as if the quest should still be offered even if it is complete.
         */
        open fun shouldOfferFromMarketInternal(market: MarketAPI): Boolean =
            stage?.invoke()?.progress != Stage.Progress.Completed
                    && !market.isBlacklisted && shouldOfferFromMarket(market)
    }
}