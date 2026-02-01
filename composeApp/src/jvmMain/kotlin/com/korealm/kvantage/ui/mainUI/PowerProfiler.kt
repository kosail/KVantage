package com.korealm.kvantage.ui.mainUI

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korealm.kvantage.state.KvandClient
import com.korealm.kvantage.utils.getSegmentedButtonShape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun PowerProfilerSection(
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
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(start = 16.dp)
        )

        if (isInitialized) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .padding(horizontal = 26.dp)
                    .heightIn(min = 48.dp),
            ) {
                val options = listOf(
                    stringResource(Res.string.power_profile_performance),
                    stringResource(Res.string.power_profile_balanced),
                    stringResource(Res.string.power_profile_powersave)
                )

                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = getSegmentedButtonShape(index, options.lastIndex),
                        onClick = {
                            selectedIndex = index
                            pendingUpdate = true
                        },
                        selected = selectedIndex == index,
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        ),
                        icon = {},
                        modifier = Modifier
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.height(48.dp)
                        ) {
                            Icon(
                                imageVector = when (index) {
                                    0 -> vectorResource(Res.drawable.bolt)
                                    1 -> vectorResource(Res.drawable.air)
                                    else -> vectorResource(Res.drawable.leaf)
                                },
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 4.dp)
                            )

                            Text(
                                text = label,
                                modifier = Modifier.padding(end = 3.dp)
                            )
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