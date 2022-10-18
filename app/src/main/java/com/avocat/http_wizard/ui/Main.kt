package com.avocat.http_wizard.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.avocat.http_wizard.ui.bottomsheet.*
import com.avocat.http_wizard.ui.component.ApiUrl
import com.avocat.http_wizard.ui.component.BottomButton
import com.avocat.http_wizard.ui.component.MadeBy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Response
import java.io.IOException


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun Main(
    apiUrlCallback: (String) -> Unit = {},
    methodChangedCallback: (String) -> Unit = {},
    sendCallback: (
        res: MutableState<Response?>,
        onResponse: (r: Response) -> Unit,
        onFailure: (e: IOException) -> Unit
    ) -> Unit,
    openTg: () -> Unit = {},
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
    )
    val coroutineScope = rememberCoroutineScope()
    val currentSheet = remember { mutableStateOf("Query") }

    val response: MutableState<Response?> = remember { mutableStateOf(null)}
    val isCallState = remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .imePadding()
                    .navigationBarsPadding()
            ) {
                when (currentSheet.value) {
                    "Query" -> Queries()
                    "Body" -> RequestBody()
                    "Response" -> Response(response)
                    "Proxy" -> Proxy()
                    "Error" -> ErrorSheet("error")
                }
            }
        },
        sheetPeekHeight = 0.dp,
        sheetElevation = 16.dp,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
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
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                ApiUrl(
                    Modifier.fillMaxWidth(0.85f),
                    apiUrlCallback,
                    methodChangedCallback,
                ) {
                    if (!isCallState.value) {
                        isCallState.value = true
                        sendCallback(
                            response, {
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
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .wrapContentHeight()
                        .imePadding()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .clip(RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
                ) {
                    for (p: Pair<String, ImageVector> in listOf(
                        Pair("Query", Icons.Outlined.QuestionMark),
                        Pair("Proxy", Icons.Outlined.Key),
                        Pair("Body", Icons.Outlined.Code)
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
        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
            bottomSheetScaffoldState.bottomSheetState.expand()
        } else {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }
}
