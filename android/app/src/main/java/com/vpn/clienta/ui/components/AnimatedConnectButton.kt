package com.vpn.clienta.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vpn.clienta.domain.VPNState
import com.vpn.clienta.ui.theme.*

@Composable
fun AnimatedConnectButton(
    state: VPNState,
    onClick: () -> Unit
) {

    val pulse = rememberInfiniteTransition()

    val scale by pulse.animateFloat(
        1f,
        1.2f,
        infiniteRepeatable(tween(900), RepeatMode.Reverse)
    )

    val color = when (state) {
        VPNState.Connected -> NordGreen
        VPNState.Connecting -> NordBlue
        VPNState.Disconnected -> NordRed
    }

    Box(
        modifier = Modifier
            .size(140.dp)
            .scale(if (state == VPNState.Connecting) scale else 1f)
            .background(color, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = when (state) {
                VPNState.Connected -> "ON"
                VPNState.Connecting -> "..."
                VPNState.Disconnected -> "CONNECT"
            },
            color = Color.White
        )
    }
}