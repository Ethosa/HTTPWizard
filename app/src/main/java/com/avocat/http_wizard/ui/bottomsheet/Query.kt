package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import com.avocat.http_wizard.obj.Query
import com.avocat.http_wizard.ui.component.Queries


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun QueriesBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    queryList: SnapshotStateList<Query>,
    onQueriesEdit: (q: SnapshotStateList<Query>) -> Unit = {},
) {
    Queries(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        queryList = queryList,
        onQueriesEdit = onQueriesEdit
    )
}