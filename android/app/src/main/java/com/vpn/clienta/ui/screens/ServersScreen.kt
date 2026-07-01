package com.vpn.clienta.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpn.clienta.core.model.VpnServer

@Composable
fun ServersScreen(
    onBack: () -> Unit = {}
) {

    val servers = listOf(
        VpnServer("Germany", "de.example.com", 443, ""),
        VpnServer("USA", "us.example.com", 443, ""),
        VpnServer("Netherlands", "nl.example.com", 443, "")
    )

    Column {

        TopAppBar(
            title = { Text("Servers") },
            navigationIcon = {
                Text(
                    text = "<",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBack() }
                )
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(servers) { server ->

                Card(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(server.name)
                        Text(server.host)
                    }
                }
            }
        }
    }
}