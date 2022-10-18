package com.avocat.http_wizard.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Api
import androidx.compose.material.icons.outlined.Http
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@Composable
fun ApiUrl(
    modifier: Modifier = Modifier,
    apiUrlCallback: (String) -> Unit = {},
    methodChangedCallback: (String) -> Unit = {},
    sendCallback: () -> Unit = {},
) {
    val url = remember { mutableStateOf(TextFieldValue("")) }
    val selected = remember { mutableStateOf("POST") }
    val expanded = remember { mutableStateOf(false) }
    val list = listOf(
        "POST",
        "GET",
        "PUT",
        "PATCH",
        "DELETE",
        "LINK",
        "UNLINK",
        "OPTIONS",
        "PURGE"
    )
    OutlinedTextField(
        modifier = modifier,
        value = url.value,
        onValueChange = {
            url.value = it
            apiUrlCallback(it.text)
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri,
            autoCorrect = false
        ),
        shape = MaterialTheme.shapes.large,
        label = { Text(text = "URL") },
        placeholder = { Text(text = "API url") },
        leadingIcon = {
            IconButton(
                onClick = {
                    expanded.value = !expanded.value
                },
                modifier = Modifier
                    .padding(12.dp, 0.dp, 0.dp, 0.dp) // leading icon left padding
                    .wrapContentSize()
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(imageVector = Icons.Outlined.Api, contentDescription = null)
                    Text(text = selected.value, style = MaterialTheme.typography.h4)
                }
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                ) {
                    list.forEach { entry ->
                        DropdownMenuItem(
                            onClick = {
                                selected.value = entry
                                expanded.value = !expanded.value
                                methodChangedCallback(entry)
                            },
                        ){
                            Text(text = entry)
                        }
                    }
                }
            }
        },
        trailingIcon = {
            IconButton(
                onClick = { sendCallback() }
            ) {
                Icon(Icons.Outlined.Send, null)
            }
        }
    )
}
