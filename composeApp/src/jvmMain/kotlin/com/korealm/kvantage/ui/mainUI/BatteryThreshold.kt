package com.korealm.kvantage.ui.mainUI

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korealm.kvantage.state.KvandClient
import com.korealm.kvantage.ui.SwitchWithText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun BatteryThreshold(
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
                    imageVector = vectorResource(Res.drawable.percentage),
                    contentDescription = stringResource(Res.string.percent_icon),
                    modifier = Modifier.size(24.dp)
                )
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
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