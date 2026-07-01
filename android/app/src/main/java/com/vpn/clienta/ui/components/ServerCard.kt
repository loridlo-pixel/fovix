package com.vpn.clienta.ui.components

import androidx.compose.runtime.Composable
import com.vpn.clienta.core.model.VpnServer

@Composable
fun ServerCard(
    server: VpnServer,
    onClick: () -> Unit
) {
    androidx.compose.material3.Card(
        onClick = onClick
    ) {
        androidx.compose.foundation.layout.Column {

            androidx.compose.material3.Text(text = server.name)
            androidx.compose.material3.Text(text = server.host)
            androidx.compose.material3.Text(text = "${server.port}")

            androidx.compose.material3.Text(
                text = server.protocol
            )
        }
    }
}