package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.fasterxml.jackson.databind.ObjectMapper
import androidx.compose.runtime.remember
import okhttp3.Response


@Composable
fun Response(res: MutableState<Response?>) {
    val body = remember { mutableStateOf(res.value?.body) }
    val data = remember { mutableStateOf(body.value!!.string()) }

    val prettyJsonString = ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data)

    LazyColumn {
        item {
            Text(prettyJsonString)
        }
    }
}