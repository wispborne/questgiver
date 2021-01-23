package wisp.questgiver

import com.fs.starfarer.api.campaign.TextPanelAPI
import com.fs.starfarer.api.ui.LabelAPI
import com.fs.starfarer.api.ui.TooltipMakerAPI
import com.fs.starfarer.api.util.Misc
import java.awt.Color

private object WispText {
    const val startTag = "<mark>"
    const val endTag = "</mark>"
    const val startTagAlt = "=="
    const val endTagAlt = "=="
    val regex = """$startTag(.*?)$endTag""".toRegex(RegexOption.DOT_MATCHES_ALL)
    val regexAlt = """$startTagAlt(.*?)$endTagAlt""".toRegex(RegexOption.DOT_MATCHES_ALL)
}

fun TextPanelAPI.addPara(
    textColor: Color = Misc.getTextColor(),
    highlightColor: Color = Misc.getHighlightColor(),
    stringMaker: ParagraphText.() -> String
): LabelAPI? {
    val string = stringMaker(ParagraphText)
    return this.addPara(
        string
            .replace(WispText.regex, "%s")
            .replace(WispText.regexAlt, "%s"),
        textColor,
        highlightColor,
        *(WispText.regex.findAll(string) + WispText.regexAlt.findAll(string))
            .map { it.groupValues[1] }
            .toList()
            .toTypedArray()
    )
}

fun TooltipMakerAPI.addPara(
    padding: Float = 10f,
    textColor: Color = Misc.getTextColor(),
    highlightColor: Color = Misc.getHighlightColor(),
    stringMaker: ParagraphText.() -> String
): LabelAPI? {
    val string = stringMaker(ParagraphText)
    return this.addPara(
        string
            .replace(WispText.regex, "%s")
            .replace(WispText.regexAlt, "%s"),
        padding,
        textColor,
        highlightColor,
        *(WispText.regex.findAll(string) + WispText.regexAlt.findAll(string))
            .map { it.groupValues[1] }
            .toList()
            .toTypedArray()
    )
}

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