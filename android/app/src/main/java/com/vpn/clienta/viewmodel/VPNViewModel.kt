package com.vpn.clienta.viewmodel

import androidx.lifecycle.ViewModel
import com.vpn.clienta.parser.VlessServer
import com.vpn.clienta.vpn.VpnEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VPNViewModel : ViewModel() {

    private val _servers = MutableStateFlow<List<VlessServer>>(emptyList())
    val servers: StateFlow<List<VlessServer>> = _servers

    private val _selected = MutableStateFlow<VlessServer?>(null)
    val selected = _selected

    init {
        loadMockServers()
    }

    private fun loadMockServers() {
        _servers.value = listOf(
            VlessServer("Test 1", "1.1.1.1", 443, "uuid-1"),
            VlessServer("Test 2", "8.8.8.8", 443, "uuid-2")
        )
    }

    fun connectToServer(server: VlessServer) {
        _selected.value = server
        VpnEngine.connect(server)
    }

    fun disconnect() {
        VpnEngine.disconnect()
    }
}