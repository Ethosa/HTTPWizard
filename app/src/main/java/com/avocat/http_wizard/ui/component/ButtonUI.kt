package com.avocat.http_wizard.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Public
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ButtonUI(
    modifier: Modifier = Modifier,
    text: String = "send",
    onClick: () -> Unit = {}
) {
    Button(
        onClick = { onClick() },
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier
    ) {
        Row {
            Icon(
                imageVector = Icons.Outlined.Public,
                contentDescription = null
            )
            Text(
                text = text,
                modifier = Modifier.padding(32.dp, 0.dp, 32.dp, 0.dp),
            )
        }
    }
}