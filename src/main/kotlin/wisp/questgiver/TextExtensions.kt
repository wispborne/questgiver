package wisp.questgiver

import com.fs.starfarer.api.campaign.TextPanelAPI
import com.fs.starfarer.api.ui.LabelAPI
import com.fs.starfarer.api.ui.TooltipMakerAPI
import com.fs.starfarer.api.util.Highlights
import com.fs.starfarer.api.util.Misc
import wisp.questgiver.Questgiver.game
import wisp.questgiver.wispLib.StringAutocorrect
import java.awt.Color

private object WispText {
    const val startTag = "<mark>"
    const val endTag = "</mark>"
    const val startTagAlt = "=="
    const val endTagAlt = "=="
    val regex = """$startTag(.*?)$endTag""".toRegex(RegexOption.DOT_MATCHES_ALL)
    val regexAlt = """$startTagAlt(.*?)$endTagAlt""".toRegex(RegexOption.DOT_MATCHES_ALL)
    val factionColorPattern = """\$${'f'}:(.+?)\{(.*?)}""".toRegex(RegexOption.DOT_MATCHES_ALL)
}

/**
 * @param textColor The non-highlight text color.
 * @param highlightColor The typical highlight color.
 * @param stringMaker A function that returns a string with placeholder variables replaced.
 */
fun TextPanelAPI.addPara(
    textColor: Color = Misc.getTextColor(),
    highlightColor: Color = Misc.getHighlightColor(),
    stringMaker: ParagraphText.() -> String
): LabelAPI? {
    val string = stringMaker(ParagraphText)
    val hlDatas = getHighlightData(string, highlightColor)

    return this.addPara(
        hlDatas.fold(string) { str, hlData ->
            str.replace(hlData.textToReplace, hlData.replacement)
        },
    )
        .also {
            this.setHighlightsInLastPara(
                Highlights().apply {
                    this.setColors(*hlDatas.map { it.highlightColor }.toTypedArray())
                    this.setText(*hlDatas.map { it.replacement }.toTypedArray())
                }
            )
        }
}

fun TooltipMakerAPI.addPara(
    padding: Float = 10f,
    textColor: Color = Misc.getTextColor(),
    highlightColor: Color = Misc.getHighlightColor(),
    stringMaker: ParagraphText.() -> String
): LabelAPI? {
    val string = stringMaker(ParagraphText)
    val hlDatas = getHighlightData(string, highlightColor)

    return this.addPara(
        hlDatas.fold(string) { str, hlData ->
            str.replace(hlData.textToReplace, hlData.replacement)
        },
        padding,
        hlDatas.map { it.highlightColor }.toTypedArray(),
        *findValuesToHighlight(string)
    )
}

private fun getHighlightData(string: String, defaultHighlightColor: Color): List<TextHighlightData> {
    return (WispText.regex.findAll(string) + WispText.regexAlt.findAll(string))
        .map {
            TextHighlightData(
                matchResult = it,
                textToReplace = it.value,
                replacement = it.groupValues[1],
                highlightColor = defaultHighlightColor
            )
        }
        .plus(
            WispText.factionColorPattern.findAll(string)
                .map {
                    TextHighlightData(
                        matchResult = it,
                        textToReplace = it.value,
                        replacement = it.groupValues[2],
                        highlightColor = StringAutocorrect.findBestFactionMatch(it.groupValues[1])?.color ?: defaultHighlightColor
                    )
                }
        )
        .sortedBy { it.matchResult.range.first }
        .toList()
}

private class TextHighlightData(
    val matchResult: MatchResult,
    val textToReplace: String,
    val replacement: String,
    val highlightColor: Color
)

private fun findValuesToHighlight(string: String) =
    (WispText.regex.findAll(string) + WispText.regexAlt.findAll(string) + WispText.factionColorPattern.findAll(string))
        .map { it.groupValues[1] }
        .toList()
        .toTypedArray()

object ParagraphText {
    fun highlight(string: String) = "${WispText.startTagAlt}$string${WispText.endTagAlt}"
    fun mark(string: String) = highlight(string)
}

object Padding {
    /**
     * The amount of padding used on the intel description panel (on the right side).
     */
    const val DESCRIPTION_PANEL = 10f

    /**
     * The amount of padding used to display intel subtitles (left side of intel panel, underneath the intel name).
     */
    const val SUBTITLE = 3f
}