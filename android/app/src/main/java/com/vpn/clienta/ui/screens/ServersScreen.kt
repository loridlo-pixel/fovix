package com.vpn.clienta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vpn.clienta.ui.components.ConnectionPanel
import com.vpn.clienta.ui.components.ServerCard
import com.vpn.clienta.viewmodel.VPNViewModel

@Composable
fun ServersScreen(viewModel: VPNViewModel) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔷 TOP PANEL (PRO)
        ConnectionPanel(
            isConnected = state.isConnected,
            serverName = state.activeServerName,
            ping = state.ping
        )

        Spacer(Modifier.height(12.dp))

        // 🔷 ACTION BAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = { viewModel.connect(context) },
                enabled = state.selected != null && !state.isConnected
            ) {
                Text(if (state.isConnecting) "CONNECTING..." else "CONNECT")
            }

            Button(
                onClick = { viewModel.disconnect(context) },
                enabled = state.isConnected
            ) {
                Text("DISCONNECT")
            }

            Button(
                onClick = { viewModel.refreshPing() }
            ) {
                Text("PING")
            }
        }

        Spacer(Modifier.height(12.dp))

        // 🔷 SERVER LIST
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(state.servers) { server ->
                ServerCard(
                    server = server,
                    selected = state.selected == server,
                    onClick = { viewModel.select(server) }
                )

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}