package wisp.questgiver.v2.json

import com.fs.starfarer.api.util.Misc
import org.json.JSONArray
import org.json.JSONObject
import org.lwjgl.input.Keyboard
import wisp.questgiver.getHighlightData
import wisp.questgiver.v2.IInteractionLogic
import wisp.questgiver.v2.OnPageShown
import wisp.questgiver.wispLib.*
import java.awt.Color
import kotlin.random.Random

/**
 * @param pagesJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).getJSONObject(questName)
 *   .getJSONArray("stages").getJSONObject(stageIndex).getJSONArray("pages")`
 */
class PagesFromJson<S : IInteractionLogic<S>>(
    pagesJson: JSONArray,
    onPageShownHandlersByPageId: Map<String, OnPageShown<S>>,
    optionConfigurator: (options: List<IInteractionLogic.Option<S>>) -> List<IInteractionLogic.Option<S>>,
    private val pages: MutableList<IInteractionLogic.Page<S>> = mutableListOf()
) : List<IInteractionLogic.Page<S>> by pages {
    init {
        pagesJson.forEach<JSONObject> { page ->
            val pageId = page.optional<String>("id")
            pages.add(
                IInteractionLogic.Page(
                    id = pageId ?: Random.nextInt().toString(),
                    image = page.optJSONObject("image")?.let {
                        IInteractionLogic.Image(
                            category = it.getObj("category"),
                            id = it.getObj("id"),
                            width = it.tryGet("width") { 128f },
                            height = it.tryGet("height") { 128f },
                            xOffset = it.tryGet("xOffset") { 0f },
                            yOffset = it.tryGet("yOffset") { 0f },
                            displayWidth = it.tryGet("displayWidth") { 128f },
                            displayHeight = it.tryGet("displayHeight") { 128f },
                        )
                    },
                    onPageShown = {
                        page.optional<JSONArray>("paras")?.forEach<String> { text ->
                            para { text.qgFormat() }
                        }

                        onPageShownHandlersByPageId[pageId]?.invoke(this)
                        page.optional<JSONObject>("onPageShown")?.run { goToPageIfPresent(this, navigator) }
                    },
                    options = page.optional<JSONArray>("options")
                        ?.map<JSONObject, IInteractionLogic.Option<S>> { optionJson ->
                            val optionId = optionJson.tryGet("id") { Misc.random.nextInt().toString() }
                            val text = optionJson.getObj<String>("text").qgFormat()
                            val highlightData = getHighlightData(text)

                            IInteractionLogic.Option(
                                id = optionId,
                                text = { highlightData.newString },
                                textColor = optionJson.optional<String>("textColor")
                                    .let { kotlin.runCatching { Color.getColor(it) }.getOrNull() }
                                    ?: highlightData.replacements.firstOrNull()?.highlightColor
                                    ?: Misc.getTextColor(),
                                tooltip = optionJson.optional<String>("tooltip")?.qgFormat()?.let { { it } },
                                shortcut = optionJson.optional<String>("shortcut")?.let { shortcut ->
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
                            ) { navigator ->
//                                    onOptionSelectedHandlersByOptionId[optionId]?.invoke(this, navigator)
                                goToPageIfPresent(optionJson, navigator)
                                navigator.refreshOptions()
                            }
                        }
                        ?.let { originalOptions ->
                            optionConfigurator.invoke(originalOptions)
                                .map { modifiedOption ->
                                    modifiedOption.copy(
                                        onOptionSelected =
                                        modifiedOption.onOptionSelected
                                            .also {
                                                originalOptions.single { it.id == modifiedOption.id }.onOptionSelected
                                            }
                                    )
                                }
                        }
                        ?: emptyList()
                )
            )
        }
    }

    private fun goToPageIfPresent(
        optionJson: JSONObject,
        navigator: IInteractionLogic.IPageNavigator<S>
    ) {
        optionJson.optional<String>("goToPage")?.let { pageId -> navigator.goToPage(pageId = pageId) }
    }
}

fun keyboardKeys(): List<String> = (0 until Keyboard.getKeyCount())
    .mapNotNull { runCatching { Keyboard.getKeyName(it) }.getOrNull() }
