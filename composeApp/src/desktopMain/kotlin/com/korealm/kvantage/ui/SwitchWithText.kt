package com.korealm.kvantage.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SwitchWithText(
    @StringRes text: StringResource,
    isChecked: Boolean,
    isEnabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
    checkedTrackColor: Color = MaterialTheme.colorScheme.primary,
    checkedThumbColor: Color = MaterialTheme.colorScheme.primaryContainer,
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
            modifier = Modifier.widthIn(max = 300.dp)
        )

        Switch(
            checked = isChecked,
            enabled = isEnabled,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 9.dp).scale(1.1F),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                checkedTrackColor = checkedTrackColor,
                checkedThumbColor = checkedThumbColor
            )
        )
    }
}