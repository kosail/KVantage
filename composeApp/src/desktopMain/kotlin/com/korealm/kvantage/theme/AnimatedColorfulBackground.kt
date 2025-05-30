import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import kotlin.math.max

// This was a hard one. I'll try to comment out the steps

@Composable
fun AnimatedColorfulBackground(modifier: Modifier = Modifier) {
    // 1) Create a reusable, infinite transition for animating floats
    val infiniteTransition = rememberInfiniteTransition()
    val duration = 15000

    // 2) Animate a float from 0f→1f→0f over 15 seconds
    val offset1 = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // 3) Animate another float in the opposite phase
    val offset2 = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Colors (using more subtle tertiary palette)
    val colors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.tertiary
    )

    // 4) One full-screen Box, drawing two radial gradients
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                // Calculate pixel dimensions
                val w = size.width
                val h = size.height
                val maxDim = max(w, h)

                // Compute centers
                val center1 = Offset(x = w * offset1.value, y = h * 0.5f)
                val center2 = Offset(x = w * 0.5f, y = h * offset2.value)

                // Radii
                val radius1 = maxDim * 0.8f
                val radius2 = maxDim * 1.2f

                // Draw first gradient
                drawRect(
                    brush   = Brush.radialGradient(colors, center = center1, radius = radius1),
                    topLeft = Offset.Zero,
                    size    = size
                )

                // Draw second gradient on top
                drawRect(
                    brush   = Brush.radialGradient(colors.reversed(), center = center2, radius = radius2),
                    topLeft = Offset.Zero,
                    size    = size
                )
            }
    )
}