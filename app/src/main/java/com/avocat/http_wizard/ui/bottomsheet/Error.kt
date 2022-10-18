package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorSheet(text: String) {
    Text(text, color = MaterialTheme.colors.onError)
}