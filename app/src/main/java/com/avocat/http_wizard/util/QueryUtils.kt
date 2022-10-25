package com.avocat.http_wizard.util

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.TextFieldValue
import com.avocat.http_wizard.obj.Query


fun rebuildQueries(
    url: MutableState<TextFieldValue>,
    queries: SnapshotStateList<Query>,
) {
    val newUrl = Uri.parse(url.value.text)
    val params = newUrl.queryParameterNames
    queries.clear()
    for (param in params) {
        queries.add(
            Query(
                TextFieldValue(param),
                TextFieldValue(newUrl.getQueryParameter(param).toString())
            )
        )
    }
}

fun rebuildUrl(
    url: MutableState<TextFieldValue>,
    queries: SnapshotStateList<Query>,
) {
    val newUrl = Uri.parse(url.value.text)
    var builder = newUrl.buildUpon()
        .clearQuery()
    for (param in queries) {
        builder = builder
            .appendQueryParameter(param.name.text, param.value.text)
    }
    url.value = TextFieldValue(builder.build().toString())
}