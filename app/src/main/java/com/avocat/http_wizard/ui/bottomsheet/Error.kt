package com.avocat.http_wizard.ui.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun ErrorSheet(text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Error")
        Text(text, color = MaterialTheme.colors.onError)
    }
}