package com.korealm.kvantage.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import java.time.LocalDate

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    isAnimatedBackground: Boolean,
    selectedThemeIndex: Int,
    appTheme: AppThemeState,
    onDismissRequest: () -> Unit = {},
    onThemeToggleAction: () -> Unit = {},
    onAnimatedBackgroundToggleAction: () -> Unit = {},
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
            modifier = Modifier.size(width = 430.dp, height = 530.dp)
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
                    modifier = Modifier.padding(15.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))

                SalutationsAndCopyright(modifier = Modifier.padding(top = 10.dp))
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