package wisp.questgiver.v2.json

import org.json.JSONObject
import wisp.questgiver.json.JSONPointer
import wisp.questgiver.json.JSONPointerException

/**
 * Creates a JSONPointer using an initialization string and tries to
 * match it to an item within this JSONObject. For example, given a
 * JSONObject initialized with this document:
 * <pre>
 * {
 * "a":{"b":"c"}
 * }
</pre> *
 * and this JSONPointer string:
 * <pre>
 * "/a/b"
</pre> *
 * Then this method will return the String "c".
 * A JSONPointerException may be thrown from code called by this method.
 *
 * @param jsonPointer string that can be used to create a JSONPointer
 * @return the item matched by the JSONPointer, otherwise null
 */
fun JSONObject.query(jsonPointer: String?): Any? {
    return query(JSONPointer(jsonPointer))
}

/**
 * Uses a user initialized JSONPointer  and tries to
 * match it to an item within this JSONObject. For example, given a
 * JSONObject initialized with this document:
 * <pre>
 * {
 * "a":{"b":"c"}
 * }
</pre> *
 * and this JSONPointer:
 * <pre>
 * "/a/b"
</pre> *
 * Then this method will return the String "c".
 * A JSONPointerException may be thrown from code called by this method.
 *
 * @param jsonPointer string that can be used to create a JSONPointer
 * @return the item matched by the JSONPointer, otherwise null
 */
fun JSONObject.query(jsonPointer: JSONPointer): Any? {
    return jsonPointer.queryFrom(this)
}

/**
 * Queries and returns a value from this object using `jsonPointer`, or
 * returns null if the query fails due to a missing key.
 *
 * @param jsonPointer the string representation of the JSON pointer
 * @return the queried value or `null`
 * @throws IllegalArgumentException if `jsonPointer` has invalid syntax
 */
fun JSONObject.optQuery(jsonPointer: String?): Any? {
    return optQuery(JSONPointer(jsonPointer))
}

/**
 * Queries and returns a value from this object using `jsonPointer`, or
 * returns null if the query fails due to a missing key.
 *
 * @param jsonPointer The JSON pointer
 * @return the queried value or `null`
 * @throws IllegalArgumentException if `jsonPointer` has invalid syntax
 */
fun JSONObject.optQuery(jsonPointer: JSONPointer): Any? {
    return try {
        jsonPointer.queryFrom(this)
    } catch (e: JSONPointerException) {
        null
    }
}