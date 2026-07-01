package com.vpn.clienta.ui.state

import com.vpn.clienta.core.model.VpnServer

data class VpnUiState(
    val isConnected: Boolean = false,
    val selectedServer: VpnServer? = null,
    val servers: List<VpnServer> = emptyList()
)