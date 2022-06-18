package wisp.questgiver

import com.fs.starfarer.api.campaign.TextPanelAPI
import com.fs.starfarer.api.ui.LabelAPI
import com.fs.starfarer.api.ui.TooltipMakerAPI
import com.fs.starfarer.api.util.Misc
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
    val allPatterns = listOf(regex, regexAlt, factionColorPattern)
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
    val hlDatas = getHighlightData(string, textColor, highlightColor)

    return this.addPara(
        hlDatas.fold(string) { str, hlData ->
            str.replace(hlData.textToReplace, hlData.replacement)
        },
    )
        .also {
            it.setHighlightColors(*hlDatas.map { it.highlightColor }.toTypedArray())
            it.setHighlight(*hlDatas.map { it.replacement }.toTypedArray())
        }
}

fun TooltipMakerAPI.addPara(
    padding: Float = 10f,
    textColor: Color = Misc.getTextColor(),
    highlightColor: Color = Misc.getHighlightColor(),
    stringMaker: ParagraphText.() -> String
): LabelAPI? {
    val string = stringMaker(ParagraphText)
    val hlDatas = getHighlightData(string, textColor, highlightColor)

    return this.addPara(
        hlDatas.fold(string) { str, hlData ->
            str.replace(hlData.textToReplace, hlData.replacement)
        },
        textColor,
        padding,
    )
        .also {
            it.setHighlightColors(*hlDatas.map { it.highlightColor }.toTypedArray())
            it.setHighlight(*hlDatas.map { it.replacement }.toTypedArray())
        }
}

private fun getHighlightData(
    string: String,
    defaultColor: Color,
    defaultHighlightColor: Color
): List<TextHighlightData> {
    return (WispText.regex.findAll(string) + WispText.regexAlt.findAll(string))
        .map {
            TextHighlightData(
                indices = it.range,
                textToReplace = it.value,
                replacement = it.groupValues[1],
                highlightColor = defaultHighlightColor
            )
        }
        .plus(
            WispText.factionColorPattern.findAll(string)
                .map {
                    TextHighlightData(
                        indices = it.range,
                        textToReplace = it.value,
                        replacement = it.groupValues[2],
                        highlightColor = StringAutocorrect.findBestFactionMatch(it.groupValues[1])?.color
                            ?: defaultHighlightColor
                    )
                }
        )
        .sortedBy { it.indices.first }
//        .let { matches ->
//            val allIndices = matches.map { it.indices }.toList()
//            val unmatchedIntBorders = mutableListOf<Int>()
//
//            string
//                .mapIndexed { index, _ ->
//                    // Map string chars to true if they were matched, false otherwise.
//                    allIndices.any { it.contains(index) }
//                }
//                .foldIndexed(false) { index, left, right ->
//                    // Each time the boolean changes, set that as an IntRange border.
//                    if (left != right) {
//                        unmatchedIntBorders.add(index)
//                    }
//                    right
//                }
//
//            unmatchedIntBorders
//                .chunked(2)
//                .map { it[0]..it[1] }
//                .map { TextHighlightData(
//                    indices = it,
//                    textToReplace = "",
//                    replacement = "",
//                    highlightColor = defaultColor
//                ) }
//        }
//        .sortedBy { it.indices.first }
        .toList()
}

private data class TextHighlightData(
    val indices: IntRange,
    val textToReplace: String,
    val replacement: String,
    val highlightColor: Color
)

//private fun findValuesToHighlight(string: String) =
//    (WispText.regex.findAll(string) + WispText.regexAlt.findAll(string) + WispText.factionColorPattern.findAll(string))
//        .map { it.groupValues[1] }
//        .toList()
//        .toTypedArray()

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