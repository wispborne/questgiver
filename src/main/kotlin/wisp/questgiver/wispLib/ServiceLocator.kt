package wisp.questgiver.wispLib

import com.fs.starfarer.api.FactoryAPI
import com.fs.starfarer.api.GameState
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.SettingsAPI
import com.fs.starfarer.api.campaign.SectorAPI
import com.fs.starfarer.api.campaign.comm.IntelManagerAPI
import com.fs.starfarer.api.combat.CombatEngineAPI


interface ServiceLocator {
    val sector: SectorAPI
    val memory: MemoryWrapper
    val intelManager: IntelManagerAPI
    val persistentData: PersistentDataWrapper
    val settings: SettingsAPI
    val logger: DebugLogger
    val combatEngine: CombatEngineAPI
    val currentState: GameState
    val factory: FactoryAPI
    var text: Text
}

open class QuestgiverServiceLocator : ServiceLocator {
    override val sector: SectorAPI
        get() = Global.getSector()

    override val memory: MemoryWrapper
        get() = MemoryWrapper(sector.memoryWithoutUpdate)

    override val intelManager: IntelManagerAPI
        get() = sector.intelManager

    override val persistentData: PersistentDataWrapper
        get() = PersistentDataWrapper

    override val settings: SettingsAPI
        get() = Global.getSettings()

    override val logger: DebugLogger
        get() = Global.getLogger(ServiceLocator::class.java)

    override val combatEngine: CombatEngineAPI
        get() = Global.getCombatEngine()

    override val currentState: GameState
        get() = Global.getCurrentState()

    override val factory: FactoryAPI
        get() = Global.getFactory()

    override var text: Text = Text(emptyList())
}