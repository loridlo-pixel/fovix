package com.vpn.clienta.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.vpn.clienta.core.model.VpnServer

@Composable
fun ServerCard(
    server: VpnServer,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = server.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "${server.host}:${server.port}",
                style = MaterialTheme.typography.bodySmall
            )

            if (selected) {
                Text("SELECTED", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}