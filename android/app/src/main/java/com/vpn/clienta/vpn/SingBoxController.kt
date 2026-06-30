package com.vpn.clienta.vpn

import android.content.Context
import android.system.Os
import android.util.Log
import java.io.File

class SingBoxController(
    private val context: Context
) {

    private var process: Process? = null

    fun start(configJson: String) {

        try {

            val binFile = File(context.filesDir, "singbox")

            if (!binFile.exists()) {
                copyBinary(binFile)
            }

            binFile.setExecutable(true)

            val configFile = File(context.filesDir, "config.json")
            configFile.writeText(configJson)

            val command = arrayOf(
                binFile.absolutePath,
                "run",
                "-c",
                configFile.absolutePath
            )

            process = ProcessBuilder(*command)
                .redirectErrorStream(true)
                .start()

            Log.d("FOVIX-SINGBOX", "STARTED")

        } catch (e: Exception) {
            Log.e("FOVIX-SINGBOX", "ERROR", e)
        }
    }

    fun stop() {
        process?.destroy()
        process = null
        Log.d("FOVIX-SINGBOX", "STOPPED")
    }

    private fun copyBinary(outFile: File) {

        context.assets.open("singbox/sing-box").use { input ->

            outFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
}