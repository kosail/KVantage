package com.korealm.kvantage.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BatteryFull
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.korealm.kvantage.state.AppThemeState
import com.korealm.kvantage.ui.theme.ThemeType
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    isAnimatedBackground: Boolean,
    isRemainingBatteryLife: Boolean,
    onDismissRequest: () -> Unit = {},
    onRemainingBatteryLifeAction: () -> Unit = {},
    onThemeToggleAction: () -> Unit = {},
    onAnimatedBackgroundToggleAction: () -> Unit = {},
    onClickThemeChange: (Int) -> Unit,
    onBatteryNameChange: (String) -> Unit = {},
    batteryName: String,
    appTheme: AppThemeState,
    selectedThemeIndex: Int
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties( usePlatformDefaultWidth = false )
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 8.dp,
            border = BorderStroke(
                5.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
            ),
            modifier = Modifier.size(width = 440.dp, height = 650.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, bottom = 30.dp),
            ) {
                Text(
                    text = stringResource(Res.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 26.dp)
                )

                Spacer(Modifier.height(30.dp))

                SwitchWithText(
                    text = Res.string.dark_mode,
                    isChecked = isDarkTheme,
                    onCheckedChange = { onThemeToggleAction() },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 26.dp))

                SwitchWithText(
                    text = Res.string.animated_background,
                    isChecked = isAnimatedBackground,
                    onCheckedChange = { onAnimatedBackgroundToggleAction() },
                    checkedTrackColor = MaterialTheme.colorScheme.secondary,
                    checkedThumbColor = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 26.dp))

                SwitchWithText(
                    text = Res.string.show_battery_life,
                    isChecked = isRemainingBatteryLife,
                    onCheckedChange = { onRemainingBatteryLifeAction() },
                    checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                    checkedThumbColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                AnimatedVisibility(isRemainingBatteryLife) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(top = 3.dp, bottom = 20.dp, start = 20.dp, end = 50.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.battery_name),
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 30.dp)
                        )

                        TextField(
                            value = batteryName,
                            onValueChange = { it -> onBatteryNameChange(it) },
                            placeholder = { batteryName },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.BatteryFull,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
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
                                .width(120.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(15.dp)
                                )
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 26.dp))


                ThemeSelector(
                    appTheme = appTheme,
                    selectedThemeIndex = selectedThemeIndex,
                    onClickThemeChange = onClickThemeChange,
                    modifier = Modifier.padding(15.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 26.dp))

                AnimatedVisibility(isRemainingBatteryLife) {
                    ShortCopyright(modifier = Modifier.padding(top = 10.dp))
                }

                AnimatedVisibility(!isRemainingBatteryLife) {
                    FullCopyright(modifier = Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}

@Composable
fun ThemeSelector(
    selectedThemeIndex: Int,
    onClickThemeChange: (Int) -> Unit,
    appTheme: AppThemeState,
    modifier: Modifier = Modifier
) {
    val themes = listOf(
        ThemeType.GRUVBOX,
        ThemeType.MATERIAL,
        ThemeType.KANAGAWA,
        ThemeType.DRACULA
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.themes),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.padding(vertical = 10.dp).heightIn(min = 48.dp)
        ) {
            themes.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index, themes.size),
                    onClick = {
                        onClickThemeChange(index)
                        appTheme.setTheme (themes[index])
                    },
                    selected = selectedThemeIndex == index,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    ),
                    modifier = Modifier
                ) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text(
                            text = label.toString().lowercase().replaceFirstChar { char -> char.titlecase() },
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

    }
}
