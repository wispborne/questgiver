package wisp.questgiver.wispLib

import java.lang.StringBuilder
import java.util.*

/**
 * A string localization class. Loads strings from a [ResourceBundle] and formats them with provided/configured substitutions.
 * Chooses the correct resource bundle based on the current [Locale].
 *
 * @param resourceBundle The [ResourceBundle] from which to load strings.
 * @param shouldThrowExceptionOnMissingValue Whether a missing value should throw an exception or merely print an error.
 * @param globalReplacementGetters A map of key-value pairs that should be read whenever getting a string by key.
 *  The value is a function that returns a key; this is convenient for getting strings that may change (such as player name)
 *  without needing to manually update the map.
 *
 */
class Text(
    var resourceBundle: ResourceBundle,
    var shouldThrowExceptionOnMissingValue: Boolean = true,
    val globalReplacementGetters: MutableMap<String, (String) -> Any?> = mutableMapOf()
) {
    companion object {
        private val pattern = """\$\{(\w+)}""".toRegex().toPattern()
    }

    @Deprecated("Use [getf]", replaceWith = ReplaceWith("getf(key, values)"))
    @JvmOverloads
    fun fmt(key: String, values: Map<String, Any?> = emptyMap()): String {
        return formatString(resourceBundle.getString(key), values)
    }

    /**
     * Get a value by its key and formats it with the provided substitutions.
     */
    fun getf(key: String, vararg values: Pair<String, Any?>): String {
        return formatString(resourceBundle.getString(key), values.toMap())
    }

    /**
     * Get a value by its key and formats it with the provided substitutions.
     */
    fun getf(key: String, values: Map<String, Any?>): String {
        return formatString(resourceBundle.getString(key), values)
    }

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

        try {
            return String.format(formatter.toString(), *valueList.toTypedArray())
        } catch (e: IllegalFormatException) {
            throw RuntimeException("Invalid format. To get '%', use '%%' instead.", e)
        }
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