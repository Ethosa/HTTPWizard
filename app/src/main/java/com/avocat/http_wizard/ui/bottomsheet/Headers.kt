package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.avocat.http_wizard.obj.Query
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun Headers(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    headers: SnapshotStateList<Query>,
    onHeadersEdit: (headers: SnapshotStateList<Query>) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        itemsIndexed(headers) { idx, x ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = x.name,
                    onValueChange = {
                        headers[idx] = x.copy(name = it)
                        onHeadersEdit(headers)
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Name") },
                    placeholder = { Text("Edit ...") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        autoCorrect = false
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    )
                )
                OutlinedTextField(
                    value = x.value,
                    onValueChange = {
                        headers[idx] = x.copy(value = it)
                        onHeadersEdit(headers)
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Value") },
                    placeholder = { Text("Edit ...") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = if (idx < headers.size-1) ImeAction.Next else ImeAction.Done,
                        autoCorrect = false
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                            focusManager.moveFocus(FocusDirection.Left)
                        },
                        onDone = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                            keyboardController?.hide()
                        }
                    )
                )
                if (idx < headers.size-1) {
                    IconButton(onClick = { headers.removeAt(idx) }) {
                        Icon(Icons.Outlined.RemoveCircle, null)
                    }
                } else {
                    IconButton(onClick = {
                        headers.add(Query(
                            TextFieldValue(""), TextFieldValue("")
                        ))
                    }) {
                        Icon(Icons.Outlined.AddCircle, null)
                    }
                }
            }
        }
    }
}