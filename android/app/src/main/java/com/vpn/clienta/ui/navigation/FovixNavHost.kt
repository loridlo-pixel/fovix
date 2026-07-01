package com.vpn.clienta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vpn.clienta.ui.screens.HomeScreen
import com.vpn.clienta.ui.screens.ServersScreen
import com.vpn.clienta.ui.screens.SettingsScreen

@Composable
fun FovixNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onOpenServers = { navController.navigate("servers") }
            )
        }

        composable("servers") {
            ServersScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}