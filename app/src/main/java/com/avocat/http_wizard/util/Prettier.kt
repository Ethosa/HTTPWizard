package com.avocat.http_wizard.util

class Prettier {
    companion object {
        fun jsonString(source: String, indent: Int = 2, indentChar: Char = ' '): String {
            var result = ""
            var level = 0
            val levelUp = listOf('{', '[')
            val levelDown = listOf('}', ']')

            val src = source.replace("\n", "")
            val indentStr = indentChar.toString()

            for (c in src) {
                if (levelUp.contains(c)) {
                    level += 1
                    result += c + "\n" + indentStr.repeat(indent*level)
                } else if (levelDown.contains(c) && level > 0) {
                    result += "\n" + indentStr.repeat(indent * level) + c
                    level -= 1
                    result += "\n" + indentStr.repeat(indent * level)
                } else if (c == ',') {
                    result += c + "\n" + indentStr.repeat(indent * level)
                } else {
                    result += c
                }
            }
            println(result)
            return result
        }
    }
}