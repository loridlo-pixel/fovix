package com.vpn.clienta.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vpn.clienta.core.model.VpnServer
import com.vpn.clienta.vpn.VpnEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VPNViewModel : ViewModel() {

    private val _state = MutableStateFlow(VpnUiState())
    val state: StateFlow<VpnUiState> = _state

    fun setServers(list: List<VpnServer>) {
        _state.value = _state.value.copy(servers = list)
    }

    fun select(server: VpnServer) {
        _state.value = _state.value.copy(selected = server)
    }

    fun connect(context: Context) {

        val server = _state.value.selected ?: return

        _state.value = _state.value.copy(
            isConnecting = true,
            activeServerName = server.name
        )

        VpnEngine.connect(context, server)

        _state.value = _state.value.copy(
            isConnected = true,
            isConnecting = false,
            ping = fakePing()
        )
    }

    fun disconnect(context: Context) {
        VpnEngine.disconnect(context)

        _state.value = _state.value.copy(
            isConnected = false,
            isConnecting = false,
            activeServerName = null,
            ping = null
        )
    }

    fun refreshPing() {
        _state.value = _state.value.copy(ping = fakePing())
    }

    private fun fakePing(): Int {
        return (20..120).random()
    }
}