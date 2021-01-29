package wisp.questgiver.wispLib

import com.fs.starfarer.api.campaign.rules.MemoryAPI
import wisp.questgiver.wispLib.Questgiver.MOD_PREFIX
import wisp.questgiver.wispLib.Questgiver.game
import kotlin.reflect.KProperty

class MemoryWrapper(private val memoryApi: MemoryAPI) {
    operator fun get(key: String): Any? {
        val keyWithPrefix = createPrefixedKey(key)
        return memoryApi[keyWithPrefix] as? Any?
    }

    operator fun set(key: String, value: Any?) {
        memoryApi[createPrefixedKey(key)] = value
    }

    fun unset(key: String) {
        memoryApi.unset(createPrefixedKey(key))
    }

    private fun createPrefixedKey(key: String) = if (key.startsWith('$')) key else "$${MOD_PREFIX}_${key}"
}

class NullableMemory<T>(private val key: String?, private val defaultValue: () -> T? = { null }) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return game.memory[key ?: property.name] as? T ?: defaultValue()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        game.memory[key ?: property.name] = value
    }
}

class Memory<T>(private val key: String?, private val defaultValue: () -> T) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return game.memory[key ?: property.name] as? T ?: defaultValue()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        game.memory[key ?: property.name] = value
    }
}