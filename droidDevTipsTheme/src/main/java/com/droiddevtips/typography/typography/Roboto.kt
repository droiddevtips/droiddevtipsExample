package com.droiddevtips.typography.typography

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.droiddevtips.typography.AppFont

/**
 * This is the Google Roboto typeface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
val roboto = FontFamily(
    Font(resId = AppFont.roboto_regular),
    Font(resId = AppFont.roboto_bold, weight = FontWeight.Bold),
    Font(resId = AppFont.roboto_semibold, weight = FontWeight.SemiBold),
    Font(resId = AppFont.roboto_medium, weight = FontWeight.Medium),
    Font(resId = AppFont.roboto_extrabold, weight = FontWeight.ExtraBold),
)