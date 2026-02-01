package com.korealm.kvantage.ui.mainUI

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.korealm.kvantage.state.KvandClient
import com.korealm.kvantage.ui.SwitchWithText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.rapid_charge

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