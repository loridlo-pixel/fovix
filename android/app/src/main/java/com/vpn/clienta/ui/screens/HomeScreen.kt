package com.vpn.clienta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vpn.clienta.domain.VPNState
import com.vpn.clienta.viewmodel.VPNViewModel

@Composable
fun HomeScreen(
    viewModel: VPNViewModel,
    modifier: Modifier = Modifier
) {

    val vpnState by viewModel.vpnState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "FOVIX",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = vpnState.name,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                viewModel.onConnectClick(context)
            },
            modifier = Modifier.size(160.dp),
            shape = androidx.compose.foundation.shape.CircleShape
        ) {
            Text(
                text = when (vpnState) {
                    VPNState.Disconnected -> "CONNECT"
                    VPNState.Connecting -> "..."
                    VPNState.Connected -> "DISCONNECT"
                }
            )
        }
    }
}