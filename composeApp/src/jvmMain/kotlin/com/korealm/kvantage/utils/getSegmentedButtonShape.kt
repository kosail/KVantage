package com.korealm.kvantage.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

fun getSegmentedButtonShape(index: Int, endIndex: Int): Shape {
    return when (index) {
        0 -> {
            RoundedCornerShape(topStartPercent = 20, bottomStartPercent = 20)
        }
        endIndex -> {
            RoundedCornerShape(topEndPercent = 20, bottomEndPercent = 20)
        }
        else -> {
            RectangleShape
        }
    }
}