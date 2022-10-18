package com.avocat.http_wizard.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.avocat.http_wizard.ui.component.ApiUrl
import com.avocat.http_wizard.ui.component.BottomButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun Main(
    apiUrlCallback: (String) -> Unit = {},
    methodChangedCallback: (String) -> Unit = {},
    sendCallback: () -> Unit = {},
    openTg: () -> Unit = {}
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
    )
    val coroutineScope = rememberCoroutineScope()
    val currentSheet = remember { mutableStateOf("Query") }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .imePadding()
                    .navigationBarsPadding()
                    .statusBarsPadding()
            ) {
                when (currentSheet.value) {
                    "Query" -> Queries()
                    "Body" -> ReqBody()
                    "Response" -> Response()
                    "Proxy" -> Proxy()
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
                Row() {
                    Text("made by")
                    Text(
                        " @ethosa",
                        modifier = Modifier
                            .clickable { openTg() }
                            .graphicsLayer(alpha = 0.99f)
                            .drawWithCache {
                                val b = Brush.horizontalGradient(listOf(
                                    Color(0xFF50D3E4), Color(0xFF3CF7C5)
                                ))
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(b, blendMode = BlendMode.SrcAtop)
                                }
                            }
                    )
                    Text(" with ♥")
                }
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
                    sendCallback = {
                        sendCallback()
                    }
                )
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .statusBarsPadding()
                    .imePadding()
                    .navigationBarsPadding()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                ) {
                    BottomButton(
                        Modifier.weight(1f),
                        currentSheet = currentSheet,
                        text = "Query",
                        icon = Icons.Outlined.QuestionMark,
                        coroutineScope = coroutineScope,
                        bottomSheetScaffoldState = bottomSheetScaffoldState
                    )
                    BottomButton(
                        Modifier.weight(1f),
                        currentSheet = currentSheet,
                        text = "Proxy",
                        icon = Icons.Outlined.Key,
                        coroutineScope = coroutineScope,
                        bottomSheetScaffoldState = bottomSheetScaffoldState
                    )
                    BottomButton(
                        Modifier.weight(1f),
                        currentSheet = currentSheet,
                        text = "Body",
                        icon = Icons.Outlined.Code,
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


@Composable
fun Queries() {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("query")
        Text("query")
        Text("query")
        Text("query")
        Text("query")
        Text("query")
        Text("query")
    }
}


@Composable
fun ReqBody() {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("body")
        Text("body")
        Text("body")
        Text("body")
        Text("body")
        Text("body")
        Text("body")
    }
}


@Composable
fun Response() {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("res")
        Text("res")
        Text("res")
        Text("res")
        Text("res")
        Text("res")
        Text("res")
    }
}


@Composable
fun Proxy() {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("proxy")
        Text("res")
        Text("res")
        Text("res")
        Text("res")
        Text("res")
        Text("res")
    }
}
