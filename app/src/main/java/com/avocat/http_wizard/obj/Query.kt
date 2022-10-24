package com.avocat.http_wizard.obj

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue

@Stable
data class Query(
    var name: TextFieldValue = TextFieldValue(""),
    var value: TextFieldValue = TextFieldValue("")
)
