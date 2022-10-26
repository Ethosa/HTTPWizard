package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue


@Composable
fun RequestBody(

) {
    var headersState by remember { mutableStateOf("Raw") }
    var rawState by remember { mutableStateOf("Text") }
    var dropdownState by remember { mutableStateOf(false) }
    val textVal = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { headersState = "Raw" }) { Text("Raw") }
            Button(onClick = { dropdownState = true }, colors = ButtonDefaults.outlinedButtonColors()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(rawState)
                    Icon(Icons.Outlined.ArrowDropDown, null)
                }
                DropdownMenu(
                    expanded = dropdownState,
                    onDismissRequest = { dropdownState = false }
                ) {
                    DropdownMenuItem(onClick = { rawState = "Text" }) { Text("Text") }
                    DropdownMenuItem(onClick = { rawState = "JSON" }) { Text("JSON") }
                    DropdownMenuItem(onClick = { rawState = "XML" }) { Text("XML") }
                    DropdownMenuItem(onClick = { rawState = "HTML" }) { Text("HTML") }
                }
            }
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when (headersState) {
                    "Raw" -> RawEditor(textVal, rawState)
                }
            }
        }
    }
}


@Composable
fun RawEditor(
    textVal: MutableState<TextFieldValue>,
    rawState: String
) {
    OutlinedTextField(
        value = textVal.value,
        onValueChange = { textVal.value = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(rawState) },
        placeholder = { Text("Edit ...") },
    )
}
