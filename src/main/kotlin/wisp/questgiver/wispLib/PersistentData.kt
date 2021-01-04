package wisp.questgiver.wispLib

import wisp.questgiver.wispLib.QuestGiver.MOD_PREFIX
import wisp.questgiver.wispLib.QuestGiver.game
import kotlin.reflect.KProperty

object PersistentDataWrapper {
    operator fun get(key: String): Any? {
        val keyWithPrefix = createPrefixedKey(key)
        return game.sector.persistentData[keyWithPrefix] as? Any?
    }

    /**
     * Automatically adds mod prefix.
     */
    operator fun set(key: String, value: Any?) {
        game.sector.persistentData[createPrefixedKey(key)] = value
    }

    fun unset(key: String) {
        game.sector.persistentData.remove(createPrefixedKey(key))
    }

    private fun createPrefixedKey(key: String) = if (key.startsWith('$')) key else "$${MOD_PREFIX}_$key"
}

class PersistentNullableData<T>(private val key: String?, private val defaultValue: () -> T? = { null }) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return game.persistentData[key ?: property.name] as? T ?: defaultValue()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        game.persistentData[key ?: property.name] = value
    }
}

class PersistentData<T>(private val key: String?, private val defaultValue: () -> T) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return game.persistentData[key ?: property.name] as? T ?: defaultValue()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        game.persistentData[key ?: property.name] = value
    }
}

class PersistentMapData<T, V>(private val key: String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): MutableMap<T, V> {
        return game.persistentData[key ?: property.name] as? MutableMap<T, V> ?: mutableMapOf()
    }

    operator fun set(mapKey: T, mapValue: V) {
        game.persistentData[key] = (game.persistentData[key] as? MutableMap<T, V>)?.apply { put(mapKey, mapValue) }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        game.persistentData[key ?: property.name] = value
    }
}