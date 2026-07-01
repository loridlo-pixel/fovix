package com.vpn.clienta.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConnectionPanel(
    isConnected: Boolean,
    serverName: String?,
    ping: Int?
) {

    Card {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = if (isConnected) "🟢 CONNECTED" else "🔴 DISCONNECTED",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = serverName ?: "No server",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Ping: ${ping ?: "--"} ms",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}