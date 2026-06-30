package com.vpn.clienta.debug

import android.content.Context
import android.util.Log
import java.io.File

object SingboxDebug {

    private const val TAG = "SINGBOX_DEBUG"

    fun checkBinary(context: Context) {
        val file = File(context.filesDir, "sing-box")

        Log.d(TAG, "===== CHECK START =====")
        Log.d(TAG, "Path: ${file.absolutePath}")
        Log.d(TAG, "Exists: ${file.exists()}")
        Log.d(TAG, "Readable: ${file.canRead()}")
        Log.d(TAG, "Writable: ${file.canWrite()}")
        Log.d(TAG, "Executable: ${file.canExecute()}")

        if (!file.exists()) {
            Log.e(TAG, "❌ Binary NOT FOUND in filesDir")
            return
        }

        // пробуем дать права
        val ok = file.setExecutable(true)
        Log.d(TAG, "chmod result: $ok")

        runTest(file)
    }

    private fun runTest(file: File) {
        try {
            Log.d(TAG, "===== TRY RUN =====")

            val process = ProcessBuilder(file.absolutePath, "version")
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().readText()

            val exit = process.waitFor()

            Log.d(TAG, "Exit code: $exit")
            Log.d(TAG, "Output: $output")

            if (exit == 0) {
                Log.d(TAG, "✅ SING-BOX WORKS")
            } else {
                Log.e(TAG, "❌ SING-BOX FAILED")
            }

        } catch (e: Exception) {
            Log.e(TAG, "❌ CRASH WHILE RUNNING BINARY", e)
        }
    }
}