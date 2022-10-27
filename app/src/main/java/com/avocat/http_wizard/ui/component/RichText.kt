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
    Pair(Regex("[{}\\]\\[]"), Color(0xFF27c93f)),
    Pair(Regex("\\d+"), Color(0xFFff5f56)),
    Pair(Regex("\"[^\"]+?\""), Color(0xFFffbd2e)),
    Pair(Regex("(true|false|null)"), Color(0xFF995258))
)

@Composable
fun RichText(
    src: String,
    type: RichTextType = RichTextType.JSON
) {
    val tokens =
        when (type) {
            RichTextType.JSON -> JSONTokens
        }

    Text(buildAnnotatedString {
        var i = 0
        var result: MatchResult?

        while (i < src.length) {
            var isMatched = false
            for (token in tokens) {
                result = token.first.matchAt(src, i)
                if (result != null) {
                    append(AnnotatedString(src.substring(result.range), SpanStyle(token.second)))
                    isMatched = true
                    i += result.range.last+1 - result.range.first
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
