package com.vpn.clienta.ui.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vpn.clienta.viewmodel.VPNViewModel

@Composable
fun HomeScreen(viewModel: VPNViewModel = viewModel()) {

    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = if (state.isConnected) "Connected" else "Disconnected",
            color = if (state.isConnected) Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            if (state.isConnected) viewModel.disconnect()
            else viewModel.connect()
        }) {
            Text(if (state.isConnected) "Disconnect" else "Connect")
        }

        Spacer(modifier = Modifier.height(12.dp))

        state.selectedServer?.let {
            Text("Server: ${it.name}")
        }
    }
}