package com.vpn.clienta.vpn

import android.content.Context
import java.io.File
import java.io.FileOutputStream

object SingBoxBinaryInstaller {

    private const val ASSET_PATH = "singbox/sing-box"
    private const val OUT_DIR = "singbox"
    private const val OUT_FILE = "sing-box"

    fun install(context: Context): File {

        val outDir = File(context.filesDir, OUT_DIR)
        if (!outDir.exists()) outDir.mkdirs()

        val outFile = File(outDir, OUT_FILE)

        if (outFile.exists() && outFile.length() > 0) {
            return outFile
        }

        context.assets.open(ASSET_PATH).use { input ->
            FileOutputStream(outFile).use { output ->
                input.copyTo(output)
            }
        }

        outFile.setExecutable(true)

        return outFile
    }
}