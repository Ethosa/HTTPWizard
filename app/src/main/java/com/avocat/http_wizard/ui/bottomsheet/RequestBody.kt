package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.avocat.http_wizard.obj.FormField
import com.avocat.http_wizard.obj.Query
import com.avocat.http_wizard.ui.component.FormData
import com.avocat.http_wizard.ui.component.Queries
import com.avocat.http_wizard.ui.component.RichTextField
import com.avocat.http_wizard.util.HighLighter


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RequestBody(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    formUrlencoded: SnapshotStateList<Query>,
    formData: SnapshotStateList<FormField>,
    onFormUrlencodedEdit: (headers: SnapshotStateList<Query>) -> Unit = {},
    onFormDataChanged: (headers: SnapshotStateList<FormField>) -> Unit = {},
) {
    var headersState by remember { mutableStateOf("raw") }
    var rawState by remember { mutableStateOf("Text") }
    var dropdownState by remember { mutableStateOf(false) }
    var dropdownTypeState by remember { mutableStateOf(false) }

    val rawValue = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Request body")
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { dropdownTypeState = true }, colors = ButtonDefaults.outlinedButtonColors()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(headersState)
                    Icon(Icons.Outlined.ArrowDropDown, null)
                }
                DropdownMenu(
                    expanded = dropdownTypeState,
                    onDismissRequest = { dropdownTypeState = false }
                ) {
                    for (i in listOf("form-data", "x-www-form-urlencoded", "raw")) {
                        DropdownMenuItem(
                            onClick = {
                                headersState = i
                                dropdownTypeState = false
                            }
                        ) {
                            Text(i)
                        }
                    }
                }
            }
            if (headersState == "raw")
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
                                    rawState = i
                                    dropdownState = false
                                }
                            ) {
                                Text(i)
                            }
                        }
                    }
                }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (headersState) {
                "raw" -> {
                    when (rawState) {
                        "Text" -> OutlinedTextField(
                            value = rawValue.value,
                            onValueChange = {
                                rawValue.value = it
                            },
                            label = { Text("Text") }
                        )
                        "JSON" -> RichTextField(value = rawValue)
                        "XML" -> RichTextField(value = rawValue, syntax = HighLighter.Syntax.XML)
                    }
                }
                "x-www-form-urlencoded" -> {
                    Queries(
                        bottomSheetScaffoldState,
                        formUrlencoded,
                        onFormUrlencodedEdit
                    )
                }
                "form-data" -> {
                    FormData(
                        bottomSheetScaffoldState,
                        formData,
                        onFormDataChanged
                    )
                }
            }
        }
    }
}
