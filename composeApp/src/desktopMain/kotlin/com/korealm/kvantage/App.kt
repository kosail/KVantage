package com.korealm.kvantage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korealm.kvantage.theme.GruvboxTheme
import com.korealm.kvantage.theme.rememberAppThemeState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val themeState = rememberAppThemeState()

    GruvboxTheme(darkTheme = themeState.isDarkTheme) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxSize()
        ) {
            Surface (
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface, // TODO: Change this shit. Looks horrible
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Performance Profile",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(20.dp)
                    )

                    MultiChoiceSegmentedButtonRow (
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {

                    }

                    HorizontalDivider()
                }

            }
        }
    }
}