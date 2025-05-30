package com.korealm.kvantage

import AnimatedColorfulBackground
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.EnergySavingsLeaf
import androidx.compose.material.icons.rounded.Percent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korealm.kvantage.theme.GruvboxTheme
import com.korealm.kvantage.state.rememberAppThemeState
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val themeState = rememberAppThemeState()
    val iconTheme = Icons.Rounded

    GruvboxTheme(darkTheme = true) {
        AnimatedColorfulBackground(modifier = Modifier.fillMaxSize().blur(3.dp))

        Surface (
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7F),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painterResource(Res.drawable.logo),
                    contentDescription = "KVantage Logo",
                    modifier = Modifier.padding(top = 12.dp, bottom = 0.dp).size(180.dp)
                )

                PowerProfilerSection(iconTheme, Modifier)
                Spacer(Modifier.height(20.dp))
                HorizontalDivider()

                BatteryThreshold(iconTheme, Modifier)
                Spacer(Modifier.height(20.dp))
                HorizontalDivider()

                RapidCharge(modifier = Modifier)
            }
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

        SingleChoiceSegmentedButtonRow (
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

    Row (
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
                percentage = if (value > 100) { 100 } else if (value < 0) { 0 } else { it.toIntOrNull() ?: 0 }
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
                .border(width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(15.dp))
        )
    }

    AnimatedVisibility(checked) {
        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = stringResource(Res.string.battery_threshold_warning),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp,top = 5.dp).width(450.dp)
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
        modifier = modifier)
}

@Composable
fun SwitchWithText(
    @StringRes text: StringResource,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkedTrackColor: Color = MaterialTheme.colorScheme.tertiary,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = stringResource(text),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 9.dp).scale(1.1F),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                checkedTrackColor = checkedTrackColor,
            )
        )
    }
}