package wisp.questgiver

import org.json.JSONArray
import org.json.JSONObject
import org.lazywizard.lazylib.ext.json.getFloat
import org.lazywizard.lazylib.ext.json.optFloat
import kotlin.random.Random

/**
 * @param pagesJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).getJSONObject(questName)
 *   .getJSONArray("stages").getJSONObject(stageIndex).getJSONArray("pages")`
 */
class PagesFromJson<S : InteractionDefinition<S>>(
    pagesJson: JSONArray,
    onPageShownHandlersByPageId: Map<String, OnPageShown<S>>,
    onOptionSelectedHandlersByPageId: Map<String, OnOptionSelected<S>>,
    private val pages: MutableList<InteractionDefinition.Page<S>> = mutableListOf()
) : List<InteractionDefinition.Page<S>> by pages {
    init {
        pagesJson.forEach<JSONObject> { page ->
            val pageId = page.optString("id")
            pages.add(
                InteractionDefinition.Page(
                    id = pageId ?: Random.nextInt().toString(),
                    image = page.optJSONObject("image")?.let {
                        InteractionDefinition.Image(
                            category = it.optString("category"),
                            id = it.optString("id"),
                            width = it.optFloat("width"),
                            height = it.optFloat("height"),
                            xOffset = it.optFloat("xOffset"),
                            yOffset = it.optFloat("yOffset"),
                            displayWidth = it.optFloat("displayWidth"),
                            displayHeight = it.optFloat("displayHeight"),
                        )
                    },
                    onPageShown = {
                        page.optJSONArray("paras")?.forEach<String> { text ->
                            para { text }
                        }

                        onPageShownHandlersByPageId[pageId]?.invoke(this)
                    },
                    options = page.optJSONArray("options")
                        .map<JSONObject, InteractionDefinition.Option<S>> { optionJson ->
                            val optionId = optionJson.optString("id")
                            InteractionDefinition.Option(
                                id = optionId,
                                text = { optionJson.getString("text") },
                                shortcut = optionJson.optJSONObject("shortcut")?.let { shortcutJson ->
                                    InteractionDefinition.Shortcut(
                                        code = shortcutJson.getInt("code"),
                                        holdCtrl = shortcutJson.optBoolean("holdCtrl", false),
                                        holdAlt = shortcutJson.optBoolean("holdAlt", false),
                                        holdShift = shortcutJson.optBoolean("holdShift", false),
                                    )
                                },
                                showIf = { optionJson.optBoolean("showIf", true) },
                                onOptionSelected = { onOptionSelectedHandlersByPageId[optionId]?.invoke(this, it) },
                            )
                        }
                )
            )
        }
    }
}

inline fun <reified T> JSONArray.forEach(
    transform: (JSONArray, Int) -> T = { json, i -> getJsonObjFromArray(json, i) }, action: (T) -> Unit
) {
    for (i in (0 until this.length()))
        action.invoke(transform.invoke(this, i))
}

inline fun <reified T, K> JSONArray.map(
    transform: (JSONArray, Int) -> T = { json, i -> getJsonObjFromArray(json, i) }, action: (T) -> K
): List<K> {
    val results = mutableListOf<K>()

    for (i in (0 until this.length()))
        results += action.invoke(transform.invoke(this, i))

    return results
}

inline fun <reified T> getJsonObjFromArray(json: JSONArray, i: Int) =
    when (T::class) {
        String::class -> json.getString(i) as T
        Float::class -> json.getFloat(i) as T
        Int::class -> json.getInt(i) as T
        Boolean::class -> json.getBoolean(i) as T
        Double::class -> json.getDouble(i) as T
        JSONArray::class -> json.getJSONArray(i) as T
        Long::class -> json.getLong(i) as T
        else -> json.getJSONObject(i) as T
    }