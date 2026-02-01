package com.korealm.kvantage.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.copyright
import kvantage.composeapp.generated.resources.signature
import kvantage.composeapp.generated.resources.with_love
import org.jetbrains.compose.resources.stringResource
import java.time.LocalDate

@Composable
fun FullCopyright(
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

@Composable
fun ShortCopyright(
    modifier: Modifier = Modifier
) {
    val year: Int = LocalDate.now().year

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "${stringResource(Res.string.copyright)} $year — ${stringResource(Res.string.with_love)}",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
        )
    }
}