package com.avocat.http_wizard.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

class HighLighter {
    enum class Syntax {
        JSON
    }

    companion object {
        private val JSONTokens = listOf(
            Pair(Regex("[{}\\]\\[]"), Color(0xFF27c93f)),
            Pair(Regex("\\d+"), Color(0xFFff5f56)),
            Pair(Regex("\"[^\"]+?\""), Color(0xFFffbd2e)),
            Pair(Regex("(true|false|null)"), Color(0xFF995258))
        )

        private fun highlightString(
            src: String,
            tokens: List<Pair<Regex, Color>>
        ): AnnotatedString {
            return buildAnnotatedString {
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
            }
        }

        fun highlightString(src: String, syntax: Syntax): AnnotatedString {
            when(syntax) {
                Syntax.JSON -> return highlightString(src, JSONTokens)
            }
        }
    }
}