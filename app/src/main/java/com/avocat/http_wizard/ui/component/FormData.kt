package com.avocat.http_wizard.ui.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.avocat.http_wizard.obj.FormField
import kotlinx.coroutines.launch


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun FormData(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    formData: SnapshotStateList<FormField>,
    onFormDataChanged: (formData: SnapshotStateList<FormField>) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(formData) { idx, x ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = x.name,
                    onValueChange = {
                        formData[idx] = x.copy(name = it)
                        onFormDataChanged(formData)
                    },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    ),
                    label = { Text("Key") },
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                // value
                if (x.type == FormField.Type.Text) {
                    OutlinedTextField(
                        value = x.value,
                        onValueChange = {
                            formData[idx] = x.copy(value = it)
                            onFormDataChanged(formData)
                        },
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            imeAction = if (idx < formData.size - 1) ImeAction.Next else ImeAction.Done
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
                        ),
                        label = { Text("Value") },
                        modifier = Modifier.weight(1f),
                    )
                } else {
                    val result = remember { mutableStateOf<Uri?>(null) }
                    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
                        result.value = it
                    }
                    Button(
                        onClick = {
                            launcher.launch(arrayOf("*/*"))
                            result.value?.let {
                                formData[idx] = x.copy(file = it.toString())
                                println(it.toString())
                            }
                        }
                    ) {
                        Text("Choose file")
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val checked = remember { mutableStateOf(x.type == FormField.Type.File) }
                    Checkbox(
                        checked = checked.value,
                        onCheckedChange = {
                            checked.value = it
                            formData[idx] = x.copy(
                                type = if (checked.value) FormField.Type.File else FormField.Type.Text
                            )
                        }
                    )
                    Text("Is file")
                }
                if (idx == formData.size-1) {
                    IconButton(
                        onClick = {
                            formData.add(FormField())
                            onFormDataChanged(formData)
                        }
                    ) {
                        Icon(Icons.Outlined.AddCircle, null)
                    }
                } else {
                    IconButton(
                        onClick = {
                            formData.removeAt(idx)
                            onFormDataChanged(formData)
                        }
                    ) {
                        Icon(Icons.Outlined.RemoveCircle, null)
                    }
                }
            }
            if (idx < formData.size-1) {
                Spacer(Modifier.height(8.dp))
                Divider(Modifier.height(1.dp))
            }
        }
    }
}
