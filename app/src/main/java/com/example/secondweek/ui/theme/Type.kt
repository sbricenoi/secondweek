package com.example.secondweek.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        lineBreak = LineBreak.Heading
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 20.sp
    )
)


