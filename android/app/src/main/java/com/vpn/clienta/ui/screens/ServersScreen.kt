package com.vpn.clienta.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpn.clienta.parser.VlessServer
import com.vpn.clienta.viewmodel.VPNViewModel

@Composable
fun ServersScreen(
    viewModel: VPNViewModel
) {

    val servers by viewModel.servers.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "SERVERS",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(servers) { server ->

                ServerItem(
                    server = server,
                    onClick = {
                        viewModel.connectToServer(server)
                    }
                )
            }
        }
    }
}

@Composable
fun ServerItem(
    server: VlessServer,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = server.name, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${server.host}:${server.port}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}