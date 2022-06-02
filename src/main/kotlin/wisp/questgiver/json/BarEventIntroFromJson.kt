package wisp.questgiver.json

import org.json.JSONObject
import wisp.questgiver.CreateInteractionPrompt
import wisp.questgiver.InteractionDefinition
import wisp.questgiver.TextToStartInteraction

private const val BAR_EVENT = "barEvent"

/**
 * @param pagesJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).getJSONObject(questName)
 *   .getJSONArray("stages").getJSONObject(stageIndex)`
 */
fun <S : InteractionDefinition<S>> InteractionPromptFromJson(
    stageJson: JSONObject,
): CreateInteractionPrompt<S> {
    return {
        para {
            stageJson
                .getJSONObject(BAR_EVENT)
                .getString("prompt")
        }
    }
}

/**
 * @param pagesJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).getJSONObject(questName)
 *   .getJSONArray("stages").getJSONObject(stageIndex)`
 */
fun <S : InteractionDefinition<S>> TextToStartInteractionFromJson(
    stageJson: JSONObject,
): TextToStartInteraction<S> {
    return {
        stageJson
            .getJSONObject(BAR_EVENT)
            .getString("optionText")
    }
}