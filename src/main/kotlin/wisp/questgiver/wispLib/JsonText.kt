package wisp.questgiver.wispLib

//class JsonText(private val blob: JSONObject, private val modId: String) : IText {
//    override var shouldThrowExceptionOnMissingValue: Boolean = true
//    override val globalReplacementGetters: MutableMap<String, (String) -> Any?> = mutableMapOf()
//
//    constructor(modId: String, jsonPaths: List<String>) : this(
//        blob = jsonPaths.map { jsonPath -> Global.getSettings().getMergedJSONForMod(jsonPath, modId) }
//            .reduce { acc, jsonObject ->
//                acc.accumulate(modId, jsonObject)
//            },
//        modId = modId
//    )
//
//    private val json = JsonPath.parse(blob.getJSONObject(modId).toString())
//
//    override fun getf(key: String, vararg values: Pair<String, Any?>): String {
//        return kotlin.runCatching {
//            JsonPath.read<String>(json, key)
//        }
//            .getOrNull()
//            .let { format ->
//                if (format == null) {
//                    val errMsg = "Error: missing value for \'$key\'"
//
//                    if (shouldThrowExceptionOnMissingValue) {
//                        throw NullPointerException(errMsg)
//                    } else {
//                        errMsg
//                    }
//                } else {
//                    formatString(format)
//                }
//            }
//    }
//}