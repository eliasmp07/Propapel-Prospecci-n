package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
actual fun providePlatformConfiguration(): PlatformConfiguration {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }

    return object : PlatformConfiguration {
        override val screenWidth = screenWidth
        override val screenHeight = screenHeight
        override val density = density
    }
}

@Composable
fun ProvideAndroidPlatformConfiguration(content: @Composable () -> Unit) {
    val platformConfiguration = providePlatformConfiguration()
    CompositionLocalProvider(
        LocalPlatformConfiguration provides platformConfiguration,
        content = content
    )
}