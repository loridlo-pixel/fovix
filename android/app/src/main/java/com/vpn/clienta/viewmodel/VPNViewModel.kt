package com.vpn.clienta.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpn.clienta.domain.VPNState
import com.vpn.clienta.vpn.FovixVpnService
import com.vpn.clienta.vpn.RealVpnChecker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VPNViewModel : ViewModel() {

    private val _vpnState = MutableStateFlow(VPNState.Disconnected)
    val vpnState: StateFlow<VPNState> = _vpnState

    fun connect(context: Context, host: String, port: Int, uuid: String) {

        viewModelScope.launch {

            _vpnState.value = VPNState.Connecting

            // 1. старт сервиса
            val intent = Intent(context, FovixVpnService::class.java).apply {
                putExtra("host", host)
                putExtra("port", port)
                putExtra("uuid", uuid)
            }

            ContextCompat.startForegroundService(context, intent)

            // 2. даём sing-box подняться
            delay(3500)

            // 3. ПЕРВАЯ РЕАЛЬНАЯ ПРОВЕРКА
            val firstCheck = RealVpnChecker.check()

            if (!firstCheck) {
                _vpnState.value = VPNState.Disconnected
                return@launch
            }

            // 4. повторная проверка (стабильность)
            delay(1500)

            val secondCheck = RealVpnChecker.check()

            _vpnState.value = if (secondCheck) {
                VPNState.Connected
            } else {
                VPNState.Disconnected
            }
        }
    }

    fun disconnect(context: Context) {

        val intent = Intent(context, FovixVpnService::class.java)
        context.stopService(intent)

        _vpnState.value = VPNState.Disconnected
    }
}