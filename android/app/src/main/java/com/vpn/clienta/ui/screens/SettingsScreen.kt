package com.vpn.clienta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onBack: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Settings")

        Spacer(Modifier.height(24.dp))

        Text("VPN Client A / Fovix core")

        Spacer(Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}