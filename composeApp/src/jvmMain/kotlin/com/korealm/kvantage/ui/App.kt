package com.korealm.kvantage.ui

import AnimatedColorfulBackground
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import com.korealm.kvantage.utils.SettingsManager
import com.korealm.kvantage.utils.SettingsManager.saveSettings
import com.korealm.kvantage.state.KvandClient
import com.korealm.kvantage.state.rememberAppThemeState
import com.korealm.kvantage.state.rememberBatteryLifeState
import com.korealm.kvantage.ui.mainUI.BatteryLife
import com.korealm.kvantage.ui.mainUI.BatteryThreshold
import com.korealm.kvantage.ui.mainUI.PowerProfilerSection
import com.korealm.kvantage.ui.mainUI.RapidCharge
import com.korealm.kvantage.ui.misc.ShortCopyright
import com.korealm.kvantage.ui.settings.SettingsScreen
import com.korealm.kvantage.ui.theme.AppTheme
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun App(kvand: KvandClient) {
    val savedSettings = remember { mutableStateOf(SettingsManager.loadSettings()) }

    val themeState = rememberAppThemeState( savedSettings.value.isDarkMode )
    val batteryLifeState = rememberBatteryLifeState( savedSettings.value.batteryName )

    var isSettingsOpen by remember { mutableStateOf(false) }

    /* There is a limitation that I don't know if it comes from hardware or software.
     In either way, let me explain:

     You can have both rapid charge and battery conservation options enabled at the same time.
     However, if you have batt conservation on and rapid charge off, and you turn on rapid charge, it automatically deactivates batt conservation.

     I don't know why this happens and why it does not happen in the opposite way.
     Anyway, the following state is to keep track of that eccentric event and reflect it on the GUI to avoid un-sync of the GUI with the actual models on hardware.
     */
    var isRapidChargeToggleConservation by remember { mutableStateOf(false) }

    AppTheme(
        themeType = themeState.currentTheme,
        darkTheme = themeState.isDarkTheme
    ) {
        if (savedSettings.value.isAnimatedBackground) {
            AnimatedColorfulBackground(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(3.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color =
                            if (themeState.isDarkTheme) MaterialTheme.colorScheme.surface
                            else MaterialTheme.colorScheme.surface
                    )
            )
        }

        Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (savedSettings.value.isAnimatedBackground) {
                MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
            } else {
                if (themeState.isDarkTheme) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { isSettingsOpen = !isSettingsOpen },
                        modifier = Modifier
                            .padding(top = 15.dp, end = 15.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.settings),
                            contentDescription = stringResource(Res.string.settings_button_content_description),
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(35.dp)
                        )
                    }

                    Image(
                        painterResource(if (themeState.isDarkTheme) Res.drawable.logo_light else Res.drawable.logo),
                        contentDescription = "KVantage Logo",
                        modifier = Modifier.padding(top = 12.dp, bottom = 0.dp).size(180.dp)
                    )
                }

                PowerProfilerSection(
                    kvand = kvand,
                    modifier = Modifier
                )

                HorizontalDivider(modifier = Modifier.padding(top = 24.dp, bottom = 5.dp))

                BatteryThreshold(
                    kvand = kvand,
                    isRapidChargeToggleConservation = isRapidChargeToggleConservation,
                    onChangeRapidChargeToggleConservation = { newValue -> isRapidChargeToggleConservation = newValue },
                    modifier = Modifier
                )
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 5.dp))

                RapidCharge(
                    kvand = kvand,
                    onChangeRapidChargeToggleConservation = { newValue -> isRapidChargeToggleConservation = newValue },
                    modifier = Modifier
                )

                HorizontalDivider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))

                AnimatedVisibility(savedSettings.value.isRemainingBatteryLifeVisible) {
                    BatteryLife(
                        remainingLife = batteryLifeState.batterylife,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                    )
                }
            }

            AnimatedVisibility(!savedSettings.value.isRemainingBatteryLifeVisible) {
                ShortCopyright(modifier = Modifier.padding(bottom = 10.dp))
            }
        }

        if (isSettingsOpen) {
            SettingsScreen(
                isDarkTheme = themeState.isDarkTheme,
                isAnimatedBackground = savedSettings.value.isAnimatedBackground,
                isRemainingBatteryLife = savedSettings.value.isRemainingBatteryLifeVisible,
                onDismissRequest = { isSettingsOpen = !isSettingsOpen },
                onThemeToggleAction = {
                    themeState.toggleTheme() // Toggle dark mode to trigger UI recomposition

                    // Make the change on savedSettings and save it
                    val dark = savedSettings.value.isDarkMode
                    savedSettings.value = savedSettings.value.copy(isDarkMode = !dark)
                    saveSettings(savedSettings.value)
                },
                onAnimatedBackgroundToggleAction = {
                    val animated = savedSettings.value.isAnimatedBackground
                    savedSettings.value = savedSettings.value.copy(isAnimatedBackground = !animated)
                    saveSettings(savedSettings.value)
                },
                onRemainingBatteryLifeAction = {
                    val showRemaining = savedSettings.value.isRemainingBatteryLifeVisible
                    savedSettings.value = savedSettings.value.copy(isRemainingBatteryLifeVisible = !showRemaining)
                    saveSettings(savedSettings.value)
                },
                onClickThemeChange = { index ->
                    savedSettings.value = savedSettings.value.copy(selectedThemeIndex = index)
                    saveSettings(savedSettings.value)
                },
                onBatteryNameChange = { newName ->
                    batteryLifeState.updateBatteryName(newName) // Update UI to show new battery information

                    savedSettings.value =
                        savedSettings.value.copy(batteryName = newName) // Update batt name on persistent models
                    saveSettings(savedSettings.value)
                },
                batteryName = batteryLifeState.batteryName,
                appTheme = themeState,
                selectedThemeIndex = savedSettings.value.selectedThemeIndex
            )
        }
    }
}


