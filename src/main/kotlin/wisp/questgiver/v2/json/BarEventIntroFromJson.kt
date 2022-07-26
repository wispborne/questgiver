package wisp.questgiver.v2.json

import org.json.JSONObject
import wisp.questgiver.getTextHighlightData
import wisp.questgiver.v2.BarEventLogic
import wisp.questgiver.v2.CreateInteractionPrompt
import wisp.questgiver.v2.IInteractionLogic
import wisp.questgiver.v2.TextToStartInteraction
import wisp.questgiver.wispLib.qgFormat

private const val BAR_EVENT = "barEvent"

/**
 * @param barEventJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).query("/$questName/stages/index/barEvent")`
 */
fun <S : IInteractionLogic<S>> InteractionPromptFromJson(
    barEventJson: JSONObject,
): CreateInteractionPrompt<S> {
    return {
        para {
            barEventJson
                .getString("prompt")
                .qgFormat()
        }
    }
}

/**
 * @param barEventJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).query("/$questName/stages/index/barEvent")`
 */
fun <S : IInteractionLogic<S>> TextToStartInteractionFromJson(
    barEventJson: JSONObject,
): TextToStartInteraction<S> {
    return {
        val highlightData = getTextHighlightData(
            barEventJson
                .getString("optionText")
                .qgFormat()
        )
        BarEventLogic.Option(
            text = highlightData.newString,
            textColor = highlightData.replacements.firstOrNull()?.highlightColor,
            tooltip = barEventJson.optString("tooltip", null)
        )
    }
}