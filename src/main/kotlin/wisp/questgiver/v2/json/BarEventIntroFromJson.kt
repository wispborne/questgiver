package wisp.questgiver.v2.json

import org.json.JSONObject
import wisp.questgiver.v2.CreateInteractionPrompt
import wisp.questgiver.v2.IInteractionDefinition
import wisp.questgiver.v2.TextToStartInteraction

private const val BAR_EVENT = "barEvent"

/**
 * @param pagesJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).getJSONObject(questName)
 *   .getJSONArray("stages").getJSONObject(stageIndex)`
 */
fun <S : IInteractionDefinition<S>> InteractionPromptFromJson(
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
fun <S : IInteractionDefinition<S>> TextToStartInteractionFromJson(
    stageJson: JSONObject,
): TextToStartInteraction<S> {
    return {
        stageJson
            .getJSONObject(BAR_EVENT)
            .getString("optionText")
    }
}