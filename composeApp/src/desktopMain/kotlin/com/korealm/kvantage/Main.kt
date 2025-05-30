package com.korealm.kvantage

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.korealm.kvantage.ui.App
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.favicon
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    val icon = painterResource(Res.drawable.favicon)
    Window(
        onCloseRequest = ::exitApplication,
        title = "KVantage",
        icon = icon,
        resizable = false,
        state = WindowState( size = DpSize(545.dp, 700.dp) )
    ) {
        App()
    }
}