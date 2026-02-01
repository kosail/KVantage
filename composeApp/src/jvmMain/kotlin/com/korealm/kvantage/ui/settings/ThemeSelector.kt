package com.korealm.kvantage.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.korealm.kvantage.state.AppThemeState
import com.korealm.kvantage.models.ThemeType
import com.korealm.kvantage.utils.getSegmentedButtonShape
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.themes
import org.jetbrains.compose.resources.stringResource


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
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp)
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .heightIn(min = 48.dp)
        ) {
            themes.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = getSegmentedButtonShape(index, themes.lastIndex),
                    onClick = {
                        onClickThemeChange(index)
                        appTheme.setTheme (themes[index])
                    },
                    selected = selectedThemeIndex == index,
                    icon = {},
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    ),
                    modifier = Modifier
                ) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
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