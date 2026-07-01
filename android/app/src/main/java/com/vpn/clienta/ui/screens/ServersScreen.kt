package com.vpn.clienta.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vpn.clienta.viewmodel.VPNViewModel
import com.vpn.clienta.ui.components.ServerCard

@Composable
fun ServersScreen(
    viewModel: VPNViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val servers by viewModel.servers.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {

        LazyColumn {
            items(servers) { server ->
                ServerCard(
                    server = server,
                    onClick = { viewModel.selectServer(server) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

