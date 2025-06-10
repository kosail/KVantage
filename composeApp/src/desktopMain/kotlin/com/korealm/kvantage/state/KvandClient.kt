package com.korealm.kvantage.state

import androidx.compose.ui.window.ApplicationScope
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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
                // There is a limitation of the JVM in which we CANNOT run executables from inside JAR files, so we have to copy the backend executable into /tmp
                // give it +x permissions and then run it. That's what we will do now.

                val embeddedBackend = this::class.java.getResourceAsStream("/backend/kvand") ?: throw IOException("Could not find embedded backend binary!")
                val tempFile = File.createTempFile("kvantage_service", null)
                tempFile.deleteOnExit() // auto-delete on shutdown

                // Copy the backend binary from the JAR to the temp file
                embeddedBackend.use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output)
                    }
                }

                tempFile.setExecutable(true)

                val process = ProcessBuilder(tempFile.absolutePath)
                    .redirectErrorStream(true)
                    .start()

                val writer = BufferedWriter(OutputStreamWriter(process.outputStream))
                val reader = BufferedReader(InputStreamReader(process.inputStream))

                // IMPORTANT: This will block the app until the Handshake between the backend, and the frontend
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
            javax.swing.JOptionPane.showMessageDialog(
                null,
                "Failed to initialize the backend service.\nRoot permissions are required to run this application.",
                "Critical Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            )

            System.err.println("Backend service failed to start (likely root permission issue)")
            exitProcess(1)
        }
    }

    @Synchronized
    fun sendCommand(command: String): String {
        writer.write(command)
        writer.newLine()
        writer.flush()

        val response = reader.readLine()
        println("[GUI -> kbatd] $command")
        println("[GUI <- kbatd] $response")

        return response ?: "ERROR"
    }
}
