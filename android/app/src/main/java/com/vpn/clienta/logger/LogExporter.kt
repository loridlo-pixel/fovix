package com.vpn.clienta.logger

import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object LogExporter {

    private val logFile = File(
        Environment.getExternalStorageDirectory(),
        "fovix_logs.txt"
    )

    fun log(message: String) {
        logFile.appendText(
            "${timestamp()} | $message\n"
        )
    }

    fun export(): File {
        return logFile
    }

    private fun timestamp(): String {
        return SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(Date())
    }
}