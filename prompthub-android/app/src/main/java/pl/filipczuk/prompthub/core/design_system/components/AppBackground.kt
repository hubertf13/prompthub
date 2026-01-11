package pl.filipczuk.prompthub.core.design_system.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun AppBackground(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.15f)
                .blur(100.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.hsl(215f, 0.98f, 0.61f), Color.Transparent),
                        center = Offset(w * 0.27f, h * 0.37f),
                        radius = w * 0.6f
                    ),
                    center = Offset(w * 0.27f, h * 0.37f),
                    radius = w * 0.6f
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.hsl(125f, 0.98f, 0.72f), Color.Transparent),
                        center = Offset(w * 0.97f, h * 0.21f),
                        radius = w * 0.6f
                    ),
                    center = Offset(w * 0.97f, h * 0.21f),
                    radius = w * 0.6f
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.hsl(354f, 0.98f, 0.61f), Color.Transparent),
                        center = Offset(w * 0.52f, h * 0.99f),
                        radius = w * 0.6f
                    ),
                    center = Offset(w * 0.52f, h * 0.99f),
                    radius = w * 0.6f
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.hsl(256f, 0.96f, 0.67f), Color.Transparent),
                        center = Offset(w * 0.10f, h * 0.29f),
                        radius = w * 0.6f
                    ),
                    center = Offset(w * 0.10f, h * 0.29f),
                    radius = w * 0.6f
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.hsl(38f, 0.60f, 0.74f), Color.Transparent),
                        center = Offset(w * 0.97f, h * 0.96f),
                        radius = w * 0.6f
                    ),
                    center = Offset(w * 0.97f, h * 0.96f),
                    radius = w * 0.6f
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.hsl(222f, 0.67f, 0.73f), Color.Transparent),
                        center = Offset(w * 0.33f, h * 0.50f),
                        radius = w * 0.6f
                    ),
                    center = Offset(w * 0.33f, h * 0.50f),
                    radius = w * 0.6f
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.hsl(343f, 0.68f, 0.79f), Color.Transparent),
                        center = Offset(w * 0.79f, h * 0.53f),
                        radius = w * 0.6f
                    ),
                    center = Offset(w * 0.79f, h * 0.53f),
                    radius = w * 0.6f
                )
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridSpacing = 30.dp.toPx()
            val gridColor = Color(0xFFE2E8F0).copy(alpha = 0.2f)

            var x = 0f
            while (x < size.width) {
                drawLine(
                    color = gridColor,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = 1f
                )
                x += gridSpacing
            }

            var y = 0f
            while (y < size.height) {
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1f
                )
                y += gridSpacing
            }
        }

        content()
    }
}