package wisp.questgiver.wispLib

import kotlin.properties.Delegates

open class Observable<T>(
    initialValue: T
) {
    open var value: T by Delegates.observable<T>(initialValue) { _, _, newValue ->
        observers.forEach { it.value(newValue) }
    }

    val observers = mutableMapOf<Any, Action<T>>()
}

typealias Action<T> = (newValue: T) -> Unit