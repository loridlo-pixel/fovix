package com.vpn.clienta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vpn.clienta.ui.screens.HomeScreen
import com.vpn.clienta.ui.screens.ServersScreen

@Composable
fun FexiNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen()
        }

        composable("servers") {
            ServersScreen()
        }
    }
}