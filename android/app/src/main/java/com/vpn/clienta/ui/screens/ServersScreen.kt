@Composable
fun ServersScreen(viewModel: VPNViewModel) {

    val servers by viewModel.servers.collectAsState()

    LazyColumn {

        items(servers) { server ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        viewModel.connectToServer(server)
                    }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(server.name)
                    Text("${server.host}:${server.port}")
                }
            }
        }
    }
}