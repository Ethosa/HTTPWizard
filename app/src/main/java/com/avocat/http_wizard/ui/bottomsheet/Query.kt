package com.avocat.http_wizard.ui.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.avocat.http_wizard.obj.Query
import kotlinx.coroutines.launch


@SuppressLint("MutableCollectionMutableState")
@ExperimentalMaterialApi
@Composable
fun Queries(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onQueriesEdit: (q: SnapshotStateList<Query>) -> Unit = {}
) {
    val queryList = remember { mutableStateListOf(Query()) }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(queryList) { idx, x ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = queryList[idx].name,
                    onValueChange = {
                        queryList[idx] = x.copy(name = it)
                        onQueriesEdit(queryList)
                    },
                    label = { Text("Param") },
                    placeholder = { Text("Param name") },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Right)
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = queryList[idx].value,
                    onValueChange = {
                        queryList[idx] = x.copy(value = it)
                        onQueriesEdit(queryList)
                    },
                    label = { Text("Value") },
                    placeholder = { Text("Param value") },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        imeAction = if (idx < queryList.size-1) ImeAction.Next else ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        },
                        onDone = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                )
                if (idx == queryList.size-1) {
                    IconButton(
                        onClick = {
                            queryList.add(Query())
                            onQueriesEdit(queryList)
                        }
                    ) {
                        Icon(Icons.Outlined.AddCircle, null)
                    }
                } else {
                    IconButton(
                        onClick = {
                            queryList.removeAt(idx)
                            onQueriesEdit(queryList)
                        }
                    ) {
                        Icon(Icons.Outlined.RemoveCircle, null)
                    }
                }
            }
            if (idx < queryList.size-1) {
                Spacer(Modifier.height(8.dp))
                Divider(Modifier.height(1.dp))
            }
        }
    }
}