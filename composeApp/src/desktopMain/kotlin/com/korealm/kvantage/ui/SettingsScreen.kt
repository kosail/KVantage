package com.korealm.kvantage.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.korealm.kvantage.state.AppThemeState
import com.korealm.kvantage.ui.theme.ThemeType
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.animated_background
import kvantage.composeapp.generated.resources.copyright
import kvantage.composeapp.generated.resources.dark_mode
import kvantage.composeapp.generated.resources.settings
import kvantage.composeapp.generated.resources.signature
import kvantage.composeapp.generated.resources.themes
import kvantage.composeapp.generated.resources.with_love
import org.jetbrains.compose.resources.stringResource
import java.time.LocalDate

@Composable
fun SettingsScreen(
    onDismissRequest: () -> Unit = {},
    isDarkTheme: Boolean,
    onThemeToggleAction: () -> Unit = {},
    isAnimatedBackground: Boolean,
    onAnimatedBackgroundToggleAction: () -> Unit = {},
    appTheme: AppThemeState,
    selectedThemeIndex: Int,
    onClickThemeChange: (Int) -> Unit
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
            modifier = Modifier.size(width = 430.dp, height = 500.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp, vertical = 30.dp),
            ) {
                Text(
                    text = stringResource(Res.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                )

                Spacer(Modifier.height(30.dp))

                SwitchWithText(
                    text = Res.string.dark_mode,
                    checked = isDarkTheme,
                    onCheckedChange = { onThemeToggleAction() },
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                )

                HorizontalDivider(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))

                SwitchWithText(
                    text = Res.string.animated_background,
                    checked = isAnimatedBackground,
                    onCheckedChange = { onAnimatedBackgroundToggleAction() },
                    checkedTrackColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                )

                HorizontalDivider(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))

                ThemeSelector(
                    appTheme = appTheme,
                    selectedThemeIndex = selectedThemeIndex,
                    onClickThemeChange = onClickThemeChange,
                    modifier = Modifier
                )

                HorizontalDivider(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))

                SalutationsAndCopyright(modifier = Modifier)
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

    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.themes),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
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

@Composable
fun SalutationsAndCopyright(
    modifier: Modifier = Modifier
) {
    val year: Int = LocalDate.now().year

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "${stringResource(Res.string.copyright)} $year",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
        )

        Text(
            text = stringResource(Res.string.with_love),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
        )

        Text(
            text = stringResource(Res.string.signature),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
        )
    }
}