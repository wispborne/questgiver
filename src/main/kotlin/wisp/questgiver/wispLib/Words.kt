package wisp.questgiver.wispLib

import java.lang.StringBuilder
import java.util.*

/**
 * @param resourceBundle The [ResourceBundle] from which to load strings.
 * @param shouldThrowExceptionOnMissingValue Whether a missing value should throw an exception or merely print an error.
 * @param globalReplacementGetters A map of key-value pairs that should be read whenever getting a string by key.
 *  The value is a function that returns a key; this is convenient for getting strings that may change (such as player name)
 *  without needing to manually update the map.
 *
 */
class Words(
    var resourceBundle: ResourceBundle,
    var shouldThrowExceptionOnMissingValue: Boolean = true,
    val globalReplacementGetters: MutableMap<String, (String) -> Any?> = mutableMapOf()
) {
    companion object {
        private val pattern = """\$\{(\w+)}""".toRegex().toPattern()
    }

    /**
     * Same as [format].
     */
    @JvmOverloads
    fun fmt(key: String, values: Map<String, Any?> = emptyMap()): String {
        return formatString(resourceBundle.getString(key), values)
    }

    /**
     * Get a value by its key and formats it with the provided substitutions.
     */
    @JvmOverloads
    fun format(key: String, values: Map<String, Any?> = emptyMap()): String = fmt(key, values)

    /**
     * Formats a given string with the provided substitutions.
     */
    @JvmOverloads
    fun formatString(format: String, values: Map<String, Any?> = emptyMap()): String {
        val formatter = StringBuilder(format)
        val valueList = mutableListOf<Any>()
        val matcher = pattern.matcher(format)

        while (matcher.find()) {
            val key: String = matcher.group(1)
            val formatKey = String.format("\${%s}", key)
            val index = formatter.indexOf(formatKey)

            if (index != -1) {
                formatter.replace(index, index + formatKey.length, "%s")
                val value = values[key]
                    ?: globalReplacementGetters[key]?.invoke(key)
                    ?: run {
                        val errMsg = "Error: missing value for \'$key\'"
                        if (shouldThrowExceptionOnMissingValue) throw NullPointerException(errMsg)
                        else errMsg
                    }
                valueList.add(value)
            }
        }

        return String.format(formatter.toString(), *valueList.toTypedArray())
    }

    /**
     * Get a value by its key.
     */
    operator fun get(key: String): String = fmt(key)

    /**
     * Same as `Locale.setDefault(locale)`.
     */
    fun setLocale(locale: Locale) = Locale.setDefault(locale)
}