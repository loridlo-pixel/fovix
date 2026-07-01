package com.vpn.clienta.ui.state

import com.vpn.clienta.core.model.VpnServer

data class VpnUiState(
    val servers: List<VpnServer> = emptyList(),
    val selected: VpnServer? = null,

    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,

    val activeServerName: String? = null,
    val ping: Int? = null
)