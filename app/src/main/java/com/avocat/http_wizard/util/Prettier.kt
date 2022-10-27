package com.avocat.http_wizard.util

class Prettier {
    companion object {
        fun jsonString(source: String, indent: Int = 2, indentChar: Char = ' '): String {
            var result = ""
            var level = 0
            var inString = false

            val levelUp = listOf('{', '[')
            val levelDown = listOf('}', ']')
            val src = source.replace("\n", "")
            val indentStr = indentChar.toString()

            for ((i, c) in src.withIndex()) {
                if (c == '"')
                    inString = !inString

                if (levelUp.contains(c) && !inString) {
                    level += 1
                    result += c + "\n" + indentStr.repeat(indent * level)
                } else if (levelDown.contains(c) && !inString) {
                    result += "\n" + indentStr.repeat(indent * level) + c
                    if (level > 0)
                        level -= 1
                    if (i < src.length-1 && src[i+1] != ',' && !levelDown.contains(src[i+1]))
                        result += "\n" + indentStr.repeat(indent * level)
                } else if (c == ',' && !inString) {
                    result += c
                    if (i < src.length-1 && !levelUp.contains(src[i+1]))
                        result += "\n" + indentStr.repeat(indent * level)
                    else
                        result += ' '
                } else {
                    result += c
                }
            }
            return result
        }
    }
}