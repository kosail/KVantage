package com.korealm.kvantage.ui

import AnimatedColorfulBackground
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korealm.kvantage.settings.SettingsManager
import com.korealm.kvantage.settings.SettingsManager.saveSettings
import com.korealm.kvantage.state.KvandClient
import com.korealm.kvantage.state.rememberAppThemeState
import com.korealm.kvantage.state.rememberBatteryLifeState
import com.korealm.kvantage.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(kvand: KvandClient) {
    val savedSettings = remember { mutableStateOf(SettingsManager.loadSettings()) }

    val themeState = rememberAppThemeState( savedSettings.value.isDarkMode )
    val batteryLifeState = rememberBatteryLifeState( savedSettings.value.batteryName )
    val iconTheme = Icons.Rounded

    var isSettingsOpen by remember { mutableStateOf(false) }

    /* There is a limitation that I don't know if it comes from hardware or software.
     In either way, let me explain:

     You can have both rapid charge and battery conservation options enabled at the same time.
     However, if you have batt conservation on and rapid charge off, and you turn on rapid charge, it automatically deactivates batt conservation.

     I don't know why this happens and why it does not happen in the opposite way.
     Anyway, the following state is to keep track of that eccentric event and reflect it on the GUI to avoid un-sync of the GUI with the actual settings on hardware.
     */
    var isRapidChargeToggleConservation by remember { mutableStateOf(false) }

    AppTheme(
        themeType = themeState.currentTheme,
        darkTheme = themeState.isDarkTheme
    ) {
        if (savedSettings.value.isAnimatedBackground) {
            AnimatedColorfulBackground(modifier = Modifier.fillMaxSize().blur(3.dp))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (themeState.isDarkTheme) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface
                    )
            )
        }

        Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (savedSettings.value.isAnimatedBackground) {
                MaterialTheme.colorScheme.surface.copy(alpha = 0.7F)
            } else {
                if (themeState.isDarkTheme) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5F)
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
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
                            imageVector = iconTheme.Settings,
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

                PowerProfilerSection(iconTheme, kvand, Modifier)
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 5.dp))

                BatteryThreshold(
                    iconTheme = iconTheme,
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
                    getBatteryLife(
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

                    savedSettings.value = savedSettings.value.copy(batteryName = newName) // Update batt name on persistent settings
                    saveSettings(savedSettings.value)
                },
                batteryName = batteryLifeState.batteryName,
                appTheme = themeState,
                selectedThemeIndex = savedSettings.value.selectedThemeIndex
            )
        }
    }
}

@Composable
fun PowerProfilerSection(
    iconTheme: Icons.Rounded,
    kvand: KvandClient,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var isInitialized by remember { mutableStateOf(false) }
    var pendingUpdate by remember { mutableStateOf<Boolean?>(null) }


    LaunchedEffect(Unit) {
        val result = withContext(Dispatchers.IO) {
            kvand.sendCommand("get performance")
        }

        val sanitizedResult = result.replace("\u0000", "").trim()
        selectedIndex = when (sanitizedResult) {
            "0x0" -> 1 // Extreme Performance
            "0x1" -> 0 // Intelligent Cooling (Balanced)
            else -> 2 // Power Saving
        }


        isInitialized = true // Mark as ready
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(Res.string.performance_profile),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        if (isInitialized) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.padding(horizontal = 26.dp).heightIn(min = 48.dp),
            ) {
                val options = listOf(
                    stringResource(Res.string.power_profile_performance),
                    stringResource(Res.string.power_profile_balanced),
                    stringResource(Res.string.power_profile_powersave)
                )

                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                        onClick = {
                            selectedIndex = index
                            pendingUpdate = true
                        },
                        selected = selectedIndex == index,
                        modifier = Modifier
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.height(48.dp)
                        ) {
                            Icon(
                                imageVector = when (index) {
                                    0 -> iconTheme.Bolt
                                    1 -> iconTheme.Air
                                    else -> iconTheme.EnergySavingsLeaf
                                },
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 4.dp)
                            )

                            Text(text = label, modifier = Modifier.padding(end = 3.dp))
                        }
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier  = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(modifier = Modifier)
            }

            Spacer(modifier = Modifier.height(5.dp))
        }
    }

    LaunchedEffect(pendingUpdate != null && isInitialized) {
        if (isInitialized) {
            withContext(Dispatchers.IO) {
                val mode = when (selectedIndex) {
                    0 -> 1 // Balanced = Intelligent Cooling = 0x000FB001
                    1 -> 0 // Extreme = Extreme Performance = 0x0012B001
                    else -> 2 // Power Save = Battery Saving = 0x0013B001
                }
                kvand.sendCommand("set performance $mode")
            }
            pendingUpdate = null
        }
    }
}

@Composable
fun BatteryThreshold(
    iconTheme: Icons.Rounded,
    kvand: KvandClient,
    isRapidChargeToggleConservation: Boolean,
    onChangeRapidChargeToggleConservation: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isChecked by remember { mutableStateOf(false) }
    var percentage by remember { mutableIntStateOf(80) }

    var isInitialized by remember { mutableStateOf(false) }
    var pendingUpdate by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        val result = withContext(Dispatchers.IO) {
            kvand.sendCommand("get conservation")
        }

        // The ACPI interface returns a C-style string which comes with a null terminator character.
        // Even though the output was already sanitized in the backend, it wouldn't be bad to make a double check re-sanitizing here just in case
        val sanitizedResult = result.replace("\u0000", "").trim()
        isChecked = sanitizedResult == "0x1"
        isInitialized = true // Mark as ready
    }

    if (isInitialized) {
        if (isRapidChargeToggleConservation) {
            isChecked = false
            onChangeRapidChargeToggleConservation(false)
        }

        SwitchWithText(
            text = Res.string.battery_threshold,
            isChecked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                pendingUpdate = checked
            },
            modifier = modifier
        )
    } else {
        SwitchWithText(
            text = Res.string.battery_threshold,
            isChecked = false,
            isEnabled = false,
            onCheckedChange = {},
            modifier = modifier
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().padding(top = 3.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = stringResource(Res.string.enable),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp),
            color = if (isChecked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        TextField(
            value = percentage.toString(),
            onValueChange = {
                val value = it.toIntOrNull() ?: 0
                percentage = if (value > 100) {
                    100
                } else if (value < 0) {
                    0
                } else {
                    it.toIntOrNull() ?: 0
                }
            },
//            enabled = checked,
            enabled = false,
            placeholder = { percentage.toString() },
            trailingIcon = {
                Icon(
                    imageVector = iconTheme.Percent,
                    contentDescription = stringResource(Res.string.percent_icon),
                    modifier = Modifier.size(24.dp)
                )
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .width(100.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(15.dp)
                )
        )
    }

    AnimatedVisibility(isChecked) {
        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = stringResource(Res.string.battery_threshold_warning),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp).width(450.dp)
        )
    }

    LaunchedEffect(pendingUpdate) {
        if (pendingUpdate != null && isInitialized) {
            withContext(Dispatchers.IO) {
                kvand.sendCommand("set conservation ${if (pendingUpdate == true) 1 else 0}")
            }
            pendingUpdate = null
        }
    }
}

@Composable
fun RapidCharge(
    kvand: KvandClient,
    onChangeRapidChargeToggleConservation: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isChecked by remember { mutableStateOf(false) }
    var isInitialized by remember { mutableStateOf(false) }
    var pendingUpdate by remember { mutableStateOf<Boolean?>(null) }

    // Fetch backend status once
    LaunchedEffect(Unit) {
        val result = withContext(Dispatchers.IO) {
            kvand.sendCommand("get rapid")
        }

        val sanitizedResult = result.replace("\u0000", "").trim()
        isChecked = sanitizedResult == "0x1"
        isInitialized = true // Mark as ready
    }

    // Switch UI
    if (isInitialized) {
        SwitchWithText(
            text = Res.string.rapid_charge,
            isChecked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                pendingUpdate = checked

                if (checked) onChangeRapidChargeToggleConservation(true)
            },
            checkedTrackColor = MaterialTheme.colorScheme.secondary,
            checkedThumbColor = MaterialTheme.colorScheme.secondaryContainer,
            modifier = modifier
        )
    } else {
        // Optional: show placeholder or disabled switch
        SwitchWithText(
            text = Res.string.rapid_charge,
            isChecked = false,
            onCheckedChange = {}, // no-op
            isEnabled = false, // visually disabled
            modifier = modifier
        )
    }

    // Send backend update when switch changes
    LaunchedEffect(pendingUpdate) {
        if (pendingUpdate != null && isInitialized) {
            withContext(Dispatchers.IO) {
                kvand.sendCommand("set rapid ${if (pendingUpdate == true) 1 else 0}")
            }
            pendingUpdate = null
        }
    }
}

@Composable
fun getBatteryLife(
    remainingLife: Float,
    modifier: Modifier = Modifier
) {

    Column (
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(Res.string.battery_life),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
        )

        if (remainingLife == -1F) {
            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = stringResource(Res.string.battery_interface_not_found),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp).width(450.dp)
            )
        } else if (remainingLife == 0F) {
            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = stringResource(Res.string.battery_parsing_failed),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp).width(450.dp)
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(top = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "0%",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier
                    )

                    LinearProgressIndicator(
                        progress = remainingLife / 100F,
                        color = MaterialTheme.colorScheme.tertiary,
                        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier
                            .size(300.dp, 15.dp)
                            .clip(RoundedCornerShape(25))
                    )

                    Text(
                        text = "100%",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier
                    )
                }

                Text(
                    text = "%.1f%%".format(remainingLife),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

            }
        }

    }
}