package com.avocat.http_wizard.ui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

enum class RichTextType {
    JSON
}

val JSONTokens = listOf(
    Pair(Regex("[{}]"), Color.Green),
    Pair(Regex("\\d+"), Color.Yellow),
    Pair(Regex("\"[\"]+?\""), Color.Magenta)
)

@Composable
fun RichText(
    src: String,
    type: RichTextType = RichTextType.JSON
) {
    Text(buildAnnotatedString {
        var i = 0
        var result: MatchResult?

        while (i < src.length-1) {
            var isMatched = false
            for (token in JSONTokens) {
                result = token.first.matchAt(src, i)
                if (result != null) {
                    append(AnnotatedString(src.substring(result.range), SpanStyle(token.second)))
                    isMatched = true
                    i += result.range.last+1 - result.range.first
                    println(i)
                    break
                }
            }
            if (!isMatched) {
                append(src[i])
                ++i
            }
        }
    })
}
