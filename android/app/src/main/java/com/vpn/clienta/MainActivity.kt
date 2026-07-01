package com.vpn.clienta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vpn.clienta.ui.navigation.FexiNavHost
import com.vpn.clienta.vpn.VpnEngine
import com.vpn.clienta.ui.theme.VPNClientATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // IMPORTANT: инициализация VPN слоя
        VpnEngine.init(this)

        setContent {
            VPNClientATheme {
                FexiNavHost()
            }
        }
    }
}