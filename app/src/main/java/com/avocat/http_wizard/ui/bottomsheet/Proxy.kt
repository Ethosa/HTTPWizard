package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@ExperimentalMaterialApi
@Composable
fun Proxy(
    proxyChanged: (hostname: String, port: String) -> Unit = { _, _ -> },
    host: MutableState<TextFieldValue>,
    port: MutableState<TextFieldValue>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Proxy")
        OutlinedTextField(
            value = host.value,
            onValueChange = {
                host.value = it
                proxyChanged(host.value.text, port.value.text)
            },
            label = { Text("Proxy URL") },
            placeholder = { Text("Proxy host") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done,
                autoCorrect = false
            ),
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = { Icon(Icons.Outlined.Key, null) },
            trailingIcon = {
                 TextField(
                     value = port.value,
                     onValueChange = {
                         port.value = it
                         proxyChanged(host.value.text, port.value.text)
                     },
                     modifier = Modifier
                         .width(96.dp),
                     keyboardOptions = KeyboardOptions(
                         keyboardType = KeyboardType.Uri,
                         imeAction = ImeAction.Done
                     ),
                 )
            }
        )
    }
}