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
import com.avocat.http_wizard.ui.component.RichTextField


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
                    for (i in listOf("Text", "JSON", "XML", "HTML")) {
                        DropdownMenuItem(
                            onClick = {
                                rawState = "Text"
                                dropdownState = false
                            }
                        ) {
                            Text("Text")
                        }
                    }
                }
            }
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when (headersState) {
                    "Raw" -> {
                        when (rawState) {
                            "Text" -> OutlinedTextField(
                                value = textVal.value,
                                onValueChange = {
                                    textVal.value = it
                                },
                                label = { Text("Text") }
                            )
                            "JSON" -> RichTextField(value = textVal)
                        }
                    }
                }
            }
        }
    }
}
