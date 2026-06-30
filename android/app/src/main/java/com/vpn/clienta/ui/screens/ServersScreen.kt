package com.vpn.clienta.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpn.clienta.viewmodel.VPNViewModel

@Composable
fun ServersScreen(
    viewModel: VPNViewModel
) {
    val servers by viewModel.servers.collectAsState()

    Column {

        Button(
            onClick = {
                viewModel.loadSubscription("https://your-sub-url.com/sub")
            }
        ) {
            Text("Load servers")
        }

        LazyColumn {
            items(servers) { server ->
                ServerItem(
                    server = server,
                    onClick = {
                        viewModel.select(server)
                    }
                )
            }
        }
    }
}
@Composable
fun ServerItem(
    server: `VlessServer.kt`,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = server.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${server.host}:${server.port}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}