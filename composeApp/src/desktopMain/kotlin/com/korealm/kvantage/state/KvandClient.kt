package com.korealm.kvantage.state

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.system.exitProcess

class KvandClient private constructor(
    private val process: Process,
    private val writer: BufferedWriter,
    private val reader: BufferedReader
) {
    companion object {
        private var instance: KvandClient? = null

        fun getInstance(): KvandClient {
            if (instance == null) {
                val backendPath = javaClass.getResource("/backend/kvand").path
                val process = ProcessBuilder(backendPath)
                    .redirectErrorStream(true)
                    .start()

                val writer = BufferedWriter(OutputStreamWriter(process.outputStream))
                val reader = BufferedReader(InputStreamReader(process.inputStream))


                // IMPORTANT: This will block the app until the Handshake between the backend and the frontend
                // is completed. The GUI will wait for "READY" from the backend.
                while (true) {
                    val line = reader.readLine() ?: handleBackendDeath()
                    if (line.trim() == "READY") break
                }

                instance = KvandClient(process, writer, reader)
                Runtime.getRuntime().addShutdownHook(Thread { process.destroy() }) // Stop backend on app exit
            }

            return instance!!
        }

        private fun handleBackendDeath(): Nothing {
            // I did not find a way to show a nice Compose window that notifies the user an error has occurred.
            // Thus, I'll use Swing as a fallback.

//            javax.swing.SwingUtilities.invokeLater {
                javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Failed to initialize the backend service.\nRoot permissions are required to run this application.",
                    "Critical Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                )
//            }

            throw IllegalStateException("Backend service failed to start (likely root permission issue)")
        }
    }

    @Synchronized
    fun sendCommand(command: String): String {
        writer.write(command)
        writer.newLine()
        writer.flush()

        return reader.readLine() ?: "-0x1"
    }
}
