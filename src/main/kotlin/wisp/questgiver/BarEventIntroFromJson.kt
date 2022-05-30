package wisp.questgiver

import org.json.JSONObject

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