package org.propapel.prospeccion.core.presentation.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.poppins_bold
import prospeccion.composeapp.generated.resources.poppins_light
import prospeccion.composeapp.generated.resources.poppins_medium
import prospeccion.composeapp.generated.resources.poppins_regular
import prospeccion.composeapp.generated.resources.poppins_semibold


val Typography = Typography(
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        color = SoporteSaiBlack
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        color = SoporteSaiBlack
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = SoporteSaiBlack
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        color = SoporteSaiBlack
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        color = SoporteSaiBlack
    ),
    headlineSmall = TextStyle(
        fontSize = 20.sp,
        color = SoporteSaiBlack
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        color = SoporteSaiBlack
    ),
)


val CompactTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp // Reducido de 32.sp a 24.sp para pantallas compactas
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp // Reducido de 24.sp a 18.sp para pantallas compactas
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp // Reducido de 14.sp a 12.sp para pantallas compactas
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp, // Reducido de 12.sp a 10.sp para pantallas compactas
        lineHeight = 16.sp, // Reducido de 18.sp a 16.sp para pantallas compactas
        letterSpacing = 0.1.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 8.sp, // Reducido de 12.sp a 10.sp para pantallas compactas
        lineHeight = 14.sp, // Reducido de 16.sp a 14.sp para pantallas compactas
        letterSpacing = 0.2.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    bodySmall = TextStyle(
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
)

val CompactSmallTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp // Reducido de 20.sp a 16.sp para pantallas compactas
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp // Reducido de 16.sp a 12.sp para pantallas compactas
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 8.sp // Reducido de 10.sp a 8.sp para pantallas compactas
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp, // Reducido de 10.sp a 8.sp para pantallas compactas
        lineHeight = 2.sp, // Reducido de 16.sp a 14.sp para pantallas compactas
        letterSpacing = 0.1.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp, // Reducido de 8.sp a 6.sp para pantallas compactas
        lineHeight = 8.sp, // Reducido de 10.sp a 8.sp para pantallas compactas
        letterSpacing = 0.1.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    bodySmall = TextStyle(
        fontSize = 8.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp,
    ),
    bodyLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp,
    ),
)

val CompactMediumTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp // Reducido de 26.sp a 20.sp para pantallas compactas
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp // Reducido de 20.sp a 16.sp para pantallas compactas
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp // Reducido de 14.sp a 12.sp para pantallas compactas
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp, // Reducido de 12.sp a 10.sp para pantallas compactas
        lineHeight = 18.sp, // Reducido de 20.sp a 18.sp para pantallas compactas
        letterSpacing = 0.3.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp, // Reducido de 10.sp a 8.sp para pantallas compactas
        lineHeight = 10.sp, // Reducido de 14.sp a 12.sp para pantallas compactas
        letterSpacing = 0.5.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    // Definiciones para bodySmall, bodyMedium, y bodyLarge
    bodySmall = TextStyle(
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
)



val MediumTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 38.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    // Used for Button
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.7.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    )
)

val ExpandedTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 42.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    // Used for Button
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.9.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom,
        ),
    )
)