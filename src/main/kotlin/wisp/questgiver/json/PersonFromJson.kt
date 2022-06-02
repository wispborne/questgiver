package wisp.questgiver.json

import org.json.JSONObject
import wisp.questgiver.CreateInteractionPrompt
import wisp.questgiver.InteractionDefinition
import wisp.questgiver.TextToStartInteraction

/**
 * @param pagesJson eg `Global.getSettings().getMergedJSONForMod(jsonPath, modId).getJSONObject(questName)
 *   .getJSONArray("stages").getJSONObject(stageIndex).getJsonObject("person")`
 */
//fun <S : InteractionDefinition<S>> PersonFromJson(
//    personJson: JSONObject,
//): CreateInteractionPrompt<S> {
//    return {
//        para {
//            personJson
//                .getJSONObject(BAR_EVENT)
//                .getString("prompt")
//        }
//    }
//}