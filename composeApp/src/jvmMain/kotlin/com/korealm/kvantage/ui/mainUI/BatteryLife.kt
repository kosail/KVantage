package com.korealm.kvantage.ui.mainUI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.battery_interface_not_found
import kvantage.composeapp.generated.resources.battery_life
import kvantage.composeapp.generated.resources.battery_parsing_failed
import org.jetbrains.compose.resources.stringResource

@Composable
fun BatteryLife(
    remainingLife: Float,
    modifier: Modifier = Modifier
) {

    Column (
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(Res.string.battery_life),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
        )

        if (remainingLife == -1F) {
            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = stringResource(Res.string.battery_interface_not_found),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp).width(450.dp)
            )
        } else if (remainingLife == 0F) {
            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = stringResource(Res.string.battery_parsing_failed),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 5.dp).width(450.dp)
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(top = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "0%",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier
                    )

                    LinearProgressIndicator(
                        progress = remainingLife / 100F,
                        color = MaterialTheme.colorScheme.tertiary,
                        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier
                            .size(300.dp, 15.dp)
                            .clip(RoundedCornerShape(20))
                    )

                    Text(
                        text = "100%",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier
                    )
                }

                Text(
                    text = "%.1f%%".format(remainingLife),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

            }
        }

    }
}