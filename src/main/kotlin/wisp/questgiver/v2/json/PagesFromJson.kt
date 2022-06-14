package wisp.questgiver.v2.json

import org.json.JSONArray
import org.json.JSONObject
import org.lazywizard.lazylib.ext.json.optFloat
import org.lwjgl.input.Keyboard
import wisp.questgiver.v2.IInteractionLogic
import wisp.questgiver.v2.OnOptionSelected
import wisp.questgiver.v2.OnPageShown
import wisp.questgiver.wispLib.forEach
import wisp.questgiver.wispLib.map
import wisp.questgiver.wispLib.qgFormat
import kotlin.random.Random

/**
 * @param pagesJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).getJSONObject(questName)
 *   .getJSONArray("stages").getJSONObject(stageIndex).getJSONArray("pages")`
 */
class PagesFromJson<S : IInteractionLogic<S>>(
    pagesJson: JSONArray,
    onPageShownHandlersByPageId: Map<String, OnPageShown<S>>,
    onOptionSelectedHandlersByOptionId: Map<String, OnOptionSelected<S>>,
    private val pages: MutableList<IInteractionLogic.Page<S>> = mutableListOf()
) : List<IInteractionLogic.Page<S>> by pages {
    init {
        pagesJson.forEach<JSONObject> { page ->
            val pageId = page.optString("id")
            pages.add(
                IInteractionLogic.Page(
                    id = pageId ?: Random.nextInt().toString(),
                    image = page.optJSONObject("image")?.let {
                        IInteractionLogic.Image(
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
                            para { text.qgFormat() }
                        }

                        onPageShownHandlersByPageId[pageId]?.invoke(this)
                    },
                    options = page.optJSONArray("options")
                        .map<JSONObject, IInteractionLogic.Option<S>> { optionJson ->
                            val optionId = optionJson.optString("id", null)
                            IInteractionLogic.Option(
                                id = optionId,
                                text = { optionJson.getString("text").qgFormat() },
                                shortcut = optionJson.optString("shortcut", null)?.let { shortcut ->
                                    kotlin.runCatching {
                                        IInteractionLogic.Shortcut(
                                            code = Keyboard.getKeyIndex(shortcut.uppercase()).takeIf { it > 0 }!!,
                                            holdCtrl = false,
                                            holdAlt = false,
                                            holdShift = false,
                                        )
                                    }
                                        .onFailure {
                                            throw RuntimeException(
                                                "Invalid key '$shortcut'. Options are [${keyboardKeys().joinToString()}].",
                                                it
                                            )
                                        }
                                        .getOrNull()
                                },
                                showIf = { optionJson.optBoolean("showIf", true) },
                                onOptionSelected = { navigator ->
                                    onOptionSelectedHandlersByOptionId[optionId]?.invoke(this, navigator)
                                    optionJson.opt("goToPage")?.let { pageId -> navigator.goToPage(pageId = pageId) }
                                },
                            )
                        }
                )
            )
        }
    }
}

fun keyboardKeys(): List<String> = (0 until Keyboard.getKeyCount())
    .mapNotNull { runCatching { Keyboard.getKeyName(it) }.getOrNull() }
