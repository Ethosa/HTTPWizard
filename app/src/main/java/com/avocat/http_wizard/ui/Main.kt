package com.avocat.http_wizard.ui

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.DataObject
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.avocat.http_wizard.obj.Query
import com.avocat.http_wizard.ui.bottomsheet.*
import com.avocat.http_wizard.ui.component.ApiUrl
import com.avocat.http_wizard.ui.component.BottomButton
import com.avocat.http_wizard.ui.component.MadeBy
import com.avocat.http_wizard.ui.theme.Background
import com.avocat.http_wizard.util.rebuildQueries
import com.avocat.http_wizard.util.rebuildUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Response
import java.io.IOException


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun Main(
    apiUrlCallback: (String) -> Unit = {},
    methodChangedCallback: (String) -> Unit = {},
    sendCallback: (
        onResponse: (r: Response) -> Unit,
        onFailure: (e: IOException) -> Unit
    ) -> Unit = { _, _ -> },
    openTg: () -> Unit = {},
    proxyChanged: (hostname: String, port: String) -> Unit = { _, _ -> },
    queriesChanged: (queries: SnapshotStateList<Query>) -> Unit = {},
    queryList: SnapshotStateList<Query>,
    prefs: SharedPreferences
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
    )
    val coroutineScope = rememberCoroutineScope()
    val currentSheet = remember { mutableStateOf("Query") }
    // Load queries and URL
    val url = remember { mutableStateOf(TextFieldValue(prefs.getString("url", "").toString())) }
    val queries = remember { queryList }

    val response: MutableState<Response?> = remember { mutableStateOf(null)}
    val isCallState = remember { mutableStateOf(false) }

    val host = remember { mutableStateOf(TextFieldValue(prefs.getString("proxy", "").toString())) }
    val port = remember { mutableStateOf(TextFieldValue(prefs.getString("port", "").toString())) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                when (currentSheet.value) {
                    "Query" -> Queries(
                        bottomSheetScaffoldState,
                        queries,
                    ) {
                        // Update text URL
                        queriesChanged(it)
                        rebuildUrl(url, it)
                    }
                    "Body" -> RequestBody()
                    "Response" -> Response(response)
                    "Proxy" -> Proxy(proxyChanged, host, port)
                    "Error" -> ErrorSheet("error")
                    "Headers" -> Headers()
                }
            }
        },
        sheetPeekHeight = 0.dp,
        sheetElevation = 16.dp,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
    ) {
        Surface(
            color = Background,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.padding(8.dp)
            ) {
                MadeBy(openTg)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                ApiUrl(
                    Modifier.fillMaxWidth(0.85f),
                    prefs,
                    url,
                    {
                        apiUrlCallback(it)
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                            rebuildQueries(url, queries)
                    },
                    methodChangedCallback,
                ) {
                    if (!isCallState.value) {
                        isCallState.value = true
                        sendCallback(
                            {
                                response.value = it
                                currentSheet.value = "Response"
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                                isCallState.value = false
                            }, {
                                response.value = null
                                currentSheet.value = "Error"
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                                isCallState.value = false
                            }
                        )
                    }
                }
            }

            // Nav bar
            Box(
                contentAlignment = Alignment.BottomCenter,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
                ) {
                    for (p: Pair<String, ImageVector> in listOf(
                        Pair("Query", Icons.Outlined.QuestionMark),
                        Pair("Proxy", Icons.Outlined.Key),
                        Pair("Body", Icons.Outlined.Code),
                        Pair("Headers", Icons.Outlined.DataObject),
                    ))
                        BottomButton(
                            Modifier.weight(1f),
                            currentSheet = currentSheet,
                            text = p.first,
                            icon = p.second,
                            coroutineScope = coroutineScope,
                            bottomSheetScaffoldState = bottomSheetScaffoldState
                        )
                }
            }
        }
    }
}


@ExperimentalMaterialApi
fun openSheet(
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    coroutineScope.launch {
        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
            bottomSheetScaffoldState.bottomSheetState.expand()
    }
}
