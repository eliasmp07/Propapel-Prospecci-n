package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
actual fun providePlatformConfiguration(): PlatformConfiguration {
    val simulatedWidth = 1024f
    val simulatedHeight = 768f
    val simulatedDensity = 1f

    return object : PlatformConfiguration {
        override val screenWidth = simulatedWidth
        override val screenHeight = simulatedHeight
        override val density = simulatedDensity
    }
}

@Composable
fun ProvideDesktopPlatformConfiguration(content: @Composable () -> Unit) {
    val platformConfiguration = providePlatformConfiguration()
    CompositionLocalProvider(
        LocalPlatformConfiguration provides platformConfiguration,
        content = content
    )
}