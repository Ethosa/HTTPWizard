package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import com.avocat.http_wizard.obj.Query
import com.avocat.http_wizard.ui.component.Queries

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun Headers(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    headers: SnapshotStateList<Query>,
    onHeadersEdit: (headers: SnapshotStateList<Query>) -> Unit = {}
) {
    Text("Headers")
    Queries(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        queryList = headers,
        onQueriesEdit = onHeadersEdit
    )
}