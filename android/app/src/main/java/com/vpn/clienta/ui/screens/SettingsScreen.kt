package com.vpn.clienta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpn.clienta.logger.LogExporter
import com.vpn.clienta.ui.theme.*

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NordBg)
            .padding(16.dp)
    ) {

        Text("SETTINGS", color = NordBlue)

        Spacer(Modifier.height(16.dp))

        Card(colors = CardDefaults.cardColors(NordCard)) {
            Column(Modifier.padding(12.dp)) {
                Text("Diagnostics", color = NordGray)

                Button(
                    onClick = { LogExporter.export() }
                ) {
                    Text("EXPORT LOGS")
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Card(colors = CardDefaults.cardColors(NordCard)) {
            Column(Modifier.padding(12.dp)) {
                Text("Subscription", color = NordGray)
                Text("Auto sync enabled")
            }
        }
    }
}