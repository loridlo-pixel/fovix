package com.vpn.clienta.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vpn.clienta.ui.components.FexiBottomBar
import com.vpn.clienta.ui.screens.HomeScreen
import com.vpn.clienta.ui.screens.ServersScreen
import com.vpn.clienta.ui.screens.SettingsScreen
import com.vpn.clienta.viewmodel.VPNViewModel

@Composable
fun FexiNavHost(
    modifier: Modifier = Modifier,
    vpnViewModel: VPNViewModel = viewModel()
) {

    var current by remember { mutableStateOf(FexiDestination.Home) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            FexiBottomBar(
                currentDestination = current,
                onDestinationSelected = { current = it }
            )
        }
    ) { padding ->

        when (current) {

            FexiDestination.Home -> HomeScreen(
                viewModel = vpnViewModel,
                modifier = Modifier.padding(padding)
            )

            FexiDestination.Servers -> ServersScreen(
                viewModel = vpnViewModel,
                modifier = Modifier.padding(padding)
            )

            FexiDestination.Settings -> SettingsScreen(
                modifier = Modifier.padding(padding)
            )
        }
    }
}