package com.example.unnamedai.util.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.unnamedai.R


val abel = FontFamily(
    Font(R.font.abel, FontWeight.Normal)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = abel, fontWeight = FontWeight.Normal,
    )
)