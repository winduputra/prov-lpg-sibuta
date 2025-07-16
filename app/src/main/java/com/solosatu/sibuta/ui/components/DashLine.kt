package com.solosatu.sibuta.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.solosatu.sibuta.ui.theme.SIBUTATheme

@Composable
fun DashLine(
    color: Color = MaterialTheme.colorScheme.outline,
    height: Dp = 1.dp,
    width: Dp = 10.dp,
    interval: Dp = 10.dp
) {
    val widthPx = with(LocalDensity.current) { width.toPx() }
    val intervalPx = with(LocalDensity.current) { interval.toPx() }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(widthPx, intervalPx), 0f)
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            cap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SIBUTATheme {
        DashLine()
    }
}
