package com.vpn.clienta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vpn.clienta.ui.navigation.FexiNavHost
import com.vpn.clienta.ui.theme.VPNClientATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            VPNClientATheme {
                FexiNavHost()
            }
        }
    }
}
