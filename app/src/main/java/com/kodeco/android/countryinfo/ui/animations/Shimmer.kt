package com.kodeco.android.countryinfo.ui.animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer

private const val DEFAULT_SHIMMER_DURATION_MILLIS = 1000
private const val DEFAULT_SHIMMER_DELAY_BEFORE_RESTART_MILLIS = 0
private const val GRADIENT_EDGE_ALPHA = .5f
private const val GRADIENT_STEP = .2f
private const val GRAPHICS_LAYER_ALPHA = .99f
private const val SHIMMER_SCALE = 5f

fun Modifier.shimmer(
    shimmerDurationMillis: Int = DEFAULT_SHIMMER_DURATION_MILLIS,
    delayBeforeRestartMillis: Int = DEFAULT_SHIMMER_DELAY_BEFORE_RESTART_MILLIS,
): Modifier = composed {
    if (shimmerDurationMillis <= 0) {
        throw IllegalArgumentException("Shimmer duration must be greater than 0")
    }

    val totalDurationMillis = (shimmerDurationMillis + delayBeforeRestartMillis)
    val targetValue = totalDurationMillis / shimmerDurationMillis.toFloat()
    val transition = rememberInfiniteTransition(label = "shimmer_infinite_transition")

    val animationStep by transition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = totalDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_step"
    )

    val alphaMaskGradientColorStops = arrayOf(
        animationStep to Color.Black.copy(alpha = GRADIENT_EDGE_ALPHA),
        animationStep + GRADIENT_STEP to Color.Black,
        animationStep + GRADIENT_STEP * 2 to Color.Black.copy(alpha = GRADIENT_EDGE_ALPHA)
    )

    graphicsLayer(alpha = GRAPHICS_LAYER_ALPHA).then(
        drawWithContent {
            // Creates a diagonal linear gradient using the provided color stops.
            val alphaMaskBrush = Brush.linearGradient(
                colorStops = alphaMaskGradientColorStops,
                end = Offset(x = size.width, y = size.height),
                tileMode = TileMode.Repeated,
            )

            drawContent()
            scale(SHIMMER_SCALE) {
                this@drawWithContent.drawRect(
                    brush = alphaMaskBrush,
                    blendMode = BlendMode.DstIn,
                )
            }
        }
    )
}
