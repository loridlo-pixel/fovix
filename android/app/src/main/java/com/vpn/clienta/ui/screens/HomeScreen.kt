package com.vpn.clienta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vpn.clienta.domain.VPNState
import com.vpn.clienta.ui.components.AnimatedConnectButton
import com.vpn.clienta.viewmodel.VPNViewModel
import com.vpn.clienta.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: VPNViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.vpnState.collectAsState()
    val selected by viewModel.selectedServer.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(NordBg, Color(0xFF050A14))
                )
            )
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ───────── HEADER ─────────
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    "FOVIX VPN",
                    style = MaterialTheme.typography.headlineMedium,
                    color = NordBlue
                )

                Text(
                    text = selected?.name ?: "No server selected",
                    color = NordGray
                )
            }

            // ───────── CENTRAL SHIELD ─────────
            Box(
                contentAlignment = Alignment.Center
            ) {

                // glow ring
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    NordBlue.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            ),
                            CircleShape
                        )
                )

                AnimatedConnectButton(
                    state = state,
                    onClick = {
                        when (state) {
                            VPNState.Connected -> viewModel.disconnect(context)
                            VPNState.Disconnected -> viewModel.connect(context)
                            VPNState.Connecting -> Unit
                        }
                    }
                )
            }

            // ───────── STATUS CARD ─────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp),
                colors = CardDefaults.cardColors(containerColor = NordCard)
            ) {

                Column(Modifier.padding(16.dp)) {

                    Text("STATUS", color = NordGray)

                    Text(
                        text = state.name,
                        color = when (state) {
                            VPNState.Connected -> NordGreen
                            VPNState.Connecting -> NordBlue
                            VPNState.Disconnected -> NordRed
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}