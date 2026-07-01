package com.vpn.clienta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpn.clienta.core.model.VpnServer

@Composable
fun HomeScreen(
    onOpenServers: () -> Unit
) {

    var isConnected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = if (isConnected) "CONNECTED" else "DISCONNECTED",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                isConnected = !isConnected
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isConnected) "DISCONNECT" else "CONNECT")
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onOpenServers,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SERVERS")
        }
    }
}