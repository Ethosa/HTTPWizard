package com.avocat.http_wizard.obj

import androidx.compose.ui.text.input.TextFieldValue


data class FormField(
    var name: TextFieldValue = TextFieldValue(""),
    var value: TextFieldValue = TextFieldValue(""),
    var file: String = "",
    var type: Type = Type.Text
) {
    enum class Type {
        Text,
        File
    }
}
