package com.vpn.clienta.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vpn.clienta.ui.navigation.FexiDestination

@Composable
fun FexiBottomBar(
    currentDestination: FexiDestination,
    onDestinationSelected: (FexiDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentDestination == FexiDestination.Home,
            onClick = { onDestinationSelected(FexiDestination.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
        )
        NavigationBarItem(
            selected = currentDestination == FexiDestination.Servers,
            onClick = { onDestinationSelected(FexiDestination.Servers) },
            icon = { Icon(Icons.Default.List, contentDescription = "Servers") },
            label = { Text("Servers") },
        )
        NavigationBarItem(
            selected = currentDestination == FexiDestination.Settings,
            onClick = { onDestinationSelected(FexiDestination.Settings) },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
        )
    }
}
