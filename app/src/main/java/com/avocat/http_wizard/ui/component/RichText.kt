package com.avocat.http_wizard.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.avocat.http_wizard.util.HighLighter

@Composable
fun RichText(
    src: String,
    syntax: HighLighter.Syntax = HighLighter.Syntax.JSON
) {
    Text(HighLighter.highlightString(src, syntax))
}

@Composable
fun RichTextField(
    modifier: Modifier = Modifier,
    value: MutableState<TextFieldValue>,
    syntax: HighLighter.Syntax = HighLighter.Syntax.JSON,
) {
    var text by remember {
        mutableStateOf(TextFieldValue(
            annotatedString = HighLighter.highlightString(value.value.text, syntax)
        ))
    }

    Row(
        modifier = modifier
    ) {
        Text(
            (1..text.text.split("\n").size).toList().joinToString("\n"),
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it.copy(annotatedString = HighLighter.highlightString(it.text, syntax))
            }
        )
    }
}
