package com.avocat.http_wizard.ui.bottomsheet

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.avocat.http_wizard.ui.component.RichText
import com.avocat.http_wizard.ui.theme.Background
import com.avocat.http_wizard.util.Prettier
import okhttp3.Response


@Composable
fun Response(res: MutableState<Response?>) {
    val body = remember { mutableStateOf(res.value?.body) }
    val data = remember { mutableStateOf(body.value!!.string()) }

    val clipboardManager = LocalClipboardManager.current
    val ctx = LocalContext.current

    val prettyJsonString = Prettier.jsonString(data.value)

    LazyColumn {
        item {
            RichText(prettyJsonString)
        }
    }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Surface(
            color = Background,
            shape = MaterialTheme.shapes.small,
        ) {
            IconButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(prettyJsonString))
                    Toast.makeText(ctx, "Response was copied", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(2.dp)
            ) {
                Icon(Icons.Outlined.ContentCopy, null)
            }
        }
    }
}