package wisp.questgiver.json

import org.json.JSONArray
import org.json.JSONObject
import org.lazywizard.lazylib.ext.json.optFloat
import wisp.questgiver.InteractionDefinition
import wisp.questgiver.OnOptionSelected
import wisp.questgiver.OnPageShown
import wisp.questgiver.wispLib.forEach
import wisp.questgiver.wispLib.map
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
                                onOptionSelected = { navigator ->
                                    onOptionSelectedHandlersByPageId[optionId]?.invoke(this, navigator)
                                    optionJson.opt("goToPage")?.let { pageId -> navigator.goToPage(pageId = pageId) }
                                },
                            )
                        }
                )
            )
        }
    }
}