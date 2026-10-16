package com.tarun.composeformkit.design_system.tokens

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTypography {
    val headline = TextStyle(
        fontSize = 28.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.Bold
    )

    val title = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.SemiBold
    )

    val body = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal
    )

    val label = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium
    )

    val caption = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Normal
    )
}