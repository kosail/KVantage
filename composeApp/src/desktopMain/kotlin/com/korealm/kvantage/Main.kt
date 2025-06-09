package com.korealm.kvantage

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.korealm.kvantage.state.KvandClient
import com.korealm.kvantage.ui.App
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.favicon
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    // This app needs root access to work, but it manages it to escalate it internally and securely.
    // Even though even the daemon watches out this situation to avoid undefined behavior,
    // the app GUI (this one) should NEVER be started as root for obvious security reasons.
    if (isRunningAsRoot()) forbidStartAsRoot(::exitApplication)

    // Launch daemon ONCE and hold reference
    val kvand = remember { KvandClient.getInstance() }
    val icon = painterResource(Res.drawable.favicon)

    Window(
        onCloseRequest = ::exitApplication,
        title = "KVantage",
        icon = icon,
        resizable = false,
        state = WindowState( size = DpSize(545.dp, 700.dp) )
    ) {
        App(kvand)
    }
}

fun isRunningAsRoot(): Boolean {
    return System.getProperty("user.name") == "root" ||
            System.getenv("USER") == "root" ||
            System.getenv("SUDO_UID") != null
}

fun forbidStartAsRoot(exitApp: () -> Unit) {
    javax.swing.JOptionPane.showMessageDialog(
        null,
        "Failed to initialize the backend service.\nRoot permissions are required to run this application.",
        "Critical Error",
        javax.swing.JOptionPane.ERROR_MESSAGE
    )

    exitApp()
}