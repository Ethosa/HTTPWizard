package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import okhttp3.Response


@Composable
fun Response(res: MutableState<Response?>) {
    Text(res.toString())
}