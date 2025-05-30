package com.korealm.kvantage.ui

import AnimatedColorfulBackground
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korealm.kvantage.state.rememberAppThemeState
import com.korealm.kvantage.theme.GruvboxTheme
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val themeState = rememberAppThemeState()
    val iconTheme = Icons.Rounded

    var isAnimatedBackground by remember { mutableStateOf(true) }
    var isSettingsOpen by remember { mutableStateOf(false) }

    GruvboxTheme(darkTheme = themeState.isDarkTheme) {

        if (isAnimatedBackground) {
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
            color = if (isAnimatedBackground) {
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
                modifier = Modifier.fillMaxSize()
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

                PowerProfilerSection(iconTheme, Modifier)
                HorizontalDivider(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))

                BatteryThreshold(iconTheme, Modifier)
                HorizontalDivider(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))

                RapidCharge(modifier = Modifier)
            }
        }

        if (isSettingsOpen) {
            SettingsScreen(
                onDismissRequest = { isSettingsOpen = !isSettingsOpen },
                isDarkTheme = themeState.isDarkTheme,
                onThemeToggleAction = { themeState.toggleTheme() },
                isAnimatedBackground = isAnimatedBackground,
                onAnimatedBackgroundToggleAction = { isAnimatedBackground = !isAnimatedBackground }
            )
        }
    }
}

@Composable
fun PowerProfilerSection(
    iconTheme: Icons.Rounded,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(Res.string.performance_profile),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.padding(horizontal = 26.dp).heightIn(min = 48.dp),
        ) {
            var selectedIndex by remember { mutableIntStateOf(0) }
            val options = listOf(
                stringResource(Res.string.power_profile_performance),
                stringResource(Res.string.power_profile_balanced),
                stringResource(Res.string.power_profile_powersave)
            )

            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { selectedIndex = index },
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
    }
}

@Composable
fun BatteryThreshold(
    iconTheme: Icons.Rounded,
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(false) }
    var percentage by remember { mutableIntStateOf(80) }

    SwitchWithText(
        text = Res.string.battery_threshold,
        checked = checked,
        onCheckedChange = { checked = !checked },
        checkedTrackColor = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(top = 12.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().padding(top = 3.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = stringResource(Res.string.enable),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp),
            color = if (checked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
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
            enabled = checked,
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

    AnimatedVisibility(checked) {
        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = stringResource(Res.string.battery_threshold_warning),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp).width(450.dp)
        )
    }
}

@Composable
fun RapidCharge(
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(false) }

    SwitchWithText(
        text = Res.string.rapid_charge,
        checked = checked,
        onCheckedChange = { checked = !checked },
        modifier = modifier
    )
}
