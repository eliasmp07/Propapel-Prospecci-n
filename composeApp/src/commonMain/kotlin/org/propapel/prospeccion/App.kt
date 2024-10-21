package org.propapel.prospeccion

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlack
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlackDark
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30Dark
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlueDark
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiDarkRed
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiDarkRed5
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiDarkRed5Dark
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiDarkRedDark
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiGray
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiGrayDark
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiWhite
import org.propapel.prospeccion.core.presentation.designsystem.Typography
import org.propapel.prospeccion.navigation.RootGraph

private val DarkColorScheme = darkColorScheme(
    primary = SoporteSaiBlueDark,
    background = SoporteSaiBlackDark,
    surface = SoporteSaiBlackDark,
    secondary = SoporteSaiBlackDark,
    tertiary = SoporteSaiBlackDark,
    primaryContainer = SoporteSaiBlue30Dark,
    onPrimary = SoporteSaiBlackDark,
    onBackground = SoporteSaiBlackDark,
    onSurface = SoporteSaiBlackDark,
    onSurfaceVariant = SoporteSaiGrayDark,
    error = SoporteSaiDarkRedDark,
    errorContainer = SoporteSaiDarkRed5Dark
)

private val LightColorScheme = lightColorScheme(
    primary = SoporteSaiBlue,
    background = SoporteSaiWhite,
    surface = SoporteSaiWhite,
    secondary = SoporteSaiBlack,
    tertiary = SoporteSaiBlack,
    primaryContainer = SoporteSaiBlue30,
    onPrimary = SoporteSaiBlack,
    onBackground = SoporteSaiBlack,
    onSurface = SoporteSaiBlack,
    onSurfaceVariant = SoporteSaiGray,
    error = SoporteSaiDarkRed,
    errorContainer = SoporteSaiDarkRed5
)
@Composable
@Preview
fun App(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit = {}
) {
    val colorScheme = LightColorScheme
    MaterialTheme(
        typography = Typography,
        colorScheme = colorScheme,
        content = {
            content()
        }
    )
}
