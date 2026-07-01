package com.vpn.clienta.ui.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vpn.clienta.viewmodel.VPNViewModel
import com.vpn.clienta.core.model.VpnServer

@Composable
fun ServersScreen(viewModel: VPNViewModel = viewModel()) {

    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        state.servers.forEach { server ->
            Text(
                text = server.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        viewModel.selectServer(server)
                    }
            )
        }
    }
}