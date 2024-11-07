@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package org.propapel.prospeccion

import android.app.Activity
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import org.propapel.prospeccion.core.presentation.designsystem.CompactDimens
import org.propapel.prospeccion.core.presentation.designsystem.CompactMediumDimens
import org.propapel.prospeccion.core.presentation.designsystem.CompactMediumTypography
import org.propapel.prospeccion.core.presentation.designsystem.CompactSmallDimens
import org.propapel.prospeccion.core.presentation.designsystem.CompactSmallTypography
import org.propapel.prospeccion.core.presentation.designsystem.CompactTypography
import org.propapel.prospeccion.core.presentation.designsystem.Dimens
import org.propapel.prospeccion.core.presentation.designsystem.ExpandedDimens
import org.propapel.prospeccion.core.presentation.designsystem.ExpandedTypography
import org.propapel.prospeccion.core.presentation.designsystem.MediumDimens
import org.propapel.prospeccion.core.presentation.designsystem.MediumTypography

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import org.propapel.prospeccion.core.presentation.designsystem.Typography

@Composable
fun Theme(
    activity: Activity = LocalContext.current as MainActivity,
    content: @Composable() () -> Unit = {}
) {
    val colorScheme = LightColorScheme

    val window = calculateWindowSizeClass()
    val config = LocalConfiguration.current
    var typography = CompactTypography
    var appDimens = CompactDimens
    val windowColor = activity.window

    Log.i("Dimen", "${config.screenWidthDp}")
    when (window.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            if (config.screenWidthDp <= 360) {
                appDimens = CompactSmallDimens
                typography = CompactSmallTypography
            } else if (config.screenWidthDp < 599) {
                appDimens = CompactMediumDimens
                typography = CompactMediumTypography
            } else {
                appDimens = CompactDimens
                typography = CompactTypography
            }
        }

        WindowWidthSizeClass.Medium -> {
            appDimens = MediumDimens
            typography = MediumTypography
        }

        WindowWidthSizeClass.Expanded -> {
            appDimens = ExpandedDimens
            typography = ExpandedTypography
        }

        else -> {
            appDimens = ExpandedDimens
            typography = ExpandedTypography
        }
    }

    ProvideAppUtils(appDimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}
