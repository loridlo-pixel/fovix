class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {

                composable("home") {
                    HomeScreen(
                        onNavigateServers = { navController.navigate("servers") }
                    )
                }

                composable("servers") {
                    ServersScreen()
                }

                composable("settings") {
                    SettingsScreen()
                }

                composable("qr") {
                    QrScannerScreen()
                }
            }
        }
    }
}