package com.korealm.kvantage.ui.misc

import AnimatedColorfulBackground
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.korealm.kvantage.ui.theme.whisperSeaDarkError
import com.korealm.kvantage.ui.theme.whisperSeaDarkPrimaryContainer
import com.korealm.kvantage.ui.theme.whisperSeaDarkSecondaryContainer
import com.korealm.kvantage.ui.theme.whisperSeaDarkTertiaryContainer
import com.korealm.kvantage.ui.theme.whisperSeaDarkText
import com.korealm.kvantage.ui.theme.whisperSeaLightPrimary
import com.korealm.kvantage.ui.theme.whisperSeaLightPrimaryContainer
import com.korealm.kvantage.ui.theme.whisperSeaLightSecondary
import com.korealm.kvantage.ui.theme.whisperSeaLightSecondaryContainer
import com.korealm.kvantage.ui.theme.whisperSeaLightTertiary
import com.korealm.kvantage.ui.theme.whisperSeaLightTertiaryContainer
import com.korealm.kvantage.ui.theme.whisperSeaLightText
import com.korealm.kvantage.utils.AppInstaller
import com.korealm.kvantage.utils.AppInstaller.writeFlag
import com.korealm.kvantage.utils.targetPath
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun InstallerDialog(
    onDismissRequest: () -> Unit
) {
    var showConfirmation by remember { mutableStateOf(false) }
    var userChoice by remember { mutableStateOf<Boolean?>(null) } // Track choice

    Dialog(
        onDismissRequest = {
            if (showConfirmation) onDismissRequest() // Only close after confirmation
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        AnimatedColorfulBackground(modifier = Modifier.fillMaxSize().blur(200.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .size(width = 450.dp, height = 330.dp)
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .background(color = Color.Transparent)
            ) {
                when {
                    // Initial screen
                    !showConfirmation -> {
                        Text(
                            text = stringResource(Res.string.ask_install).format("\n", "\n\n"),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 30.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                        ) {
                            Button(
                                onClick = {
                                    AppInstaller.install()
                                    showConfirmation = true
                                    userChoice = true
                                },
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = whisperSeaLightPrimaryContainer.copy(alpha = 0.5f),
                                    contentColor = whisperSeaLightText
                                ),
                                border = BorderStroke(1.dp, whisperSeaLightPrimary),
                                modifier = Modifier
                            ) { Text(text = stringResource(Res.string.okay)) }

                            Button(
                                onClick = { onDismissRequest() },
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = whisperSeaLightSecondaryContainer.copy(alpha = 0.5f),
                                    contentColor = whisperSeaLightText
                                ),
                                border = BorderStroke(1.dp, whisperSeaLightSecondary),
                                modifier = Modifier
                            ) { Text(text = stringResource(Res.string.no)) }

                            Button(
                                onClick = {
                                    writeFlag()
                                    showConfirmation = true
                                    userChoice = false
                                },
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = whisperSeaLightTertiaryContainer.copy(alpha = 0.5f),
                                    contentColor = whisperSeaLightText
                                ),
                                border = BorderStroke(1.dp, whisperSeaLightTertiary),
                                modifier = Modifier
                            ) { Text(text = stringResource(Res.string.dont_ask)) }
                        }
                    }

                    // Confirmation screen
                    showConfirmation -> {
                        Text(
                            text = when {
                                userChoice == true -> stringResource(Res.string.success_install)
                                else -> stringResource(Res.string.declined_forever_install)
                            },
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 30.dp)
                        )

                        Button(
                            onClick = { if (userChoice == true) AppInstaller.reRunApp(targetPath.toString()) else onDismissRequest() },
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = whisperSeaLightPrimaryContainer.copy(alpha = 0.5f),
                                contentColor = whisperSeaLightText
                            ),
                            border = BorderStroke(1.dp, whisperSeaLightPrimary),
                            modifier = Modifier
                        ) { Text(text = stringResource(Res.string.okay)) }
                    }
                }
            }
        }
    }
}