package com.avocat.http_wizard.util

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.TextFieldValue
import com.avocat.http_wizard.obj.Query
import java.lang.Exception


fun rebuildQueries(
    url: MutableState<TextFieldValue>,
    queries: SnapshotStateList<Query>,
) {
    val uri: Uri
    val params: Set<String>
    try {
        uri = Uri.parse(url.value.text)
        params = uri.queryParameterNames
    } catch (e: Exception) {
        return
    }
    queries.clear()
    for (param in params) {
        queries.add(
            Query(
                TextFieldValue(param),
                TextFieldValue(uri.getQueryParameter(param).toString())
            )
        )
    }
    if (queries.size == 0) {
        queries.add(Query())
    }
}

fun rebuildUrl(
    url: MutableState<TextFieldValue>,
    queries: SnapshotStateList<Query>,
) {
    val uri: Uri
    try {
        uri = Uri.parse(url.value.text)
    } catch (e: Exception) {
        return
    }
    var builder = uri.buildUpon()
        .clearQuery()
    for (param in queries) {
        builder = builder
            .appendQueryParameter(param.name.text, param.value.text)
    }
    url.value = TextFieldValue(builder.build().toString())
}