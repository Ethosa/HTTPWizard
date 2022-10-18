package com.avocat.http_wizard.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun MadeBy(
    openTg: () -> Unit = {}
) {
    Row {
        Text("made by")
        Text(
            " @ethosa",
            modifier = Modifier
                .clickable { openTg() }
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    val b = Brush.horizontalGradient(
                        listOf(
                            Color(0xFF50D3E4), Color(0xFF3CF7C5)
                        )
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(b, blendMode = BlendMode.SrcAtop)
                    }
                }
        )
        Text(" with ♥")
    }
}