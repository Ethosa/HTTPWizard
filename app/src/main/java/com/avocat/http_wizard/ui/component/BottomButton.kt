package com.avocat.http_wizard.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.avocat.http_wizard.ui.openSheet
import com.avocat.http_wizard.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    currentSheet: MutableState<String>,
    text: String,
    icon: ImageVector,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Button(
        onClick = {
            currentSheet.value = text
            openSheet(coroutineScope, bottomSheetScaffoldState)
            keyboardController?.hide()
        },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, Modifier.size(28.dp, 28.dp))
            Text(text, fontSize = Typography.h4.fontSize)
        }
    }
}