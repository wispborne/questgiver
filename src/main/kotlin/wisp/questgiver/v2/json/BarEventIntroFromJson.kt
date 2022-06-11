package wisp.questgiver.v2.json

import org.json.JSONObject
import wisp.questgiver.v2.CreateInteractionPrompt
import wisp.questgiver.v2.IInteractionLogic
import wisp.questgiver.v2.TextToStartInteraction

private const val BAR_EVENT = "barEvent"

/**
 * @param stageJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).query("/$questName/stages/stageIndex")`
 */
fun <S : IInteractionLogic<S>> InteractionPromptFromJson(
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
 * @param stageJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).query("/$questName/stages/stageIndex")`
 */
fun <S : IInteractionLogic<S>> TextToStartInteractionFromJson(
    stageJson: JSONObject,
): TextToStartInteraction<S> {
    return {
        stageJson
            .getJSONObject(BAR_EVENT)
            .getString("optionText")
    }
}