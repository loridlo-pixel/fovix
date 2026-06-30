package com.vpn.clienta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpn.clienta.domain.VPNState
import com.vpn.clienta.ui.components.ConnectButton
import com.vpn.clienta.viewmodel.VPNViewModel

@Composable
fun HomeScreen(
    viewModel: VPNViewModel,
    modifier: Modifier = Modifier,
) {
    val vpnState by viewModel.vpnState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "FEXI",
            style = MaterialTheme.typography.displaySmall,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Status: ${vpnState.displayName()}",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(48.dp))

        ConnectButton(
            onClick = viewModel::onConnectClick,
            enabled = vpnState != VPNState.Connecting,
        )
    }
}

private fun VPNState.displayName(): String = when (this) {
    VPNState.Disconnected -> "Disconnected"
    VPNState.Connecting -> "Connecting"
    VPNState.Connected -> "Connected"
}
