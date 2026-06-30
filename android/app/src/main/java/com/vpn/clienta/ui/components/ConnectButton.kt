package com.vpn.clienta.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vpn.clienta.domain.VPNState
import kotlinx.coroutines.delay

@Composable
fun ConnectButton(
    vpnState: VPNState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val color = when (vpnState) {
        VPNState.Disconnected -> Color(0xFFFF4D4D)
        VPNState.Connecting -> Color(0xFFFFC107)
        VPNState.Connected -> Color(0xFF00E676)
    }

    // 🌊 Pulse animation (breathing effect)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (vpnState == VPNState.Connecting) 1.18f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAnim"
    )

    // 🖱 press animation
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.92f else pulse,
        animationSpec = tween(120),
        label = "scale"
    )

    val text = when (vpnState) {
        VPNState.Disconnected -> "CONNECT"
        VPNState.Connecting -> "WAIT"
        VPNState.Connected -> "DISCONNECT"
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(160.dp)
            .scale(scale)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.9f),
                        Color(0xFF121826)
                    )
                ),
                shape = CircleShape
            )
            .clickable(enabled = enabled) {
                pressed = true
                onClick()
            }
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(120)
            pressed = false
        }
    }
}
