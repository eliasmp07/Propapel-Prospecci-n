package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import platform.UIKit.UIScreen

@Composable
actual fun providePlatformConfiguration(): PlatformConfiguration {
    val screen = UIScreen.mainScreen
    val density = screen.scale.toFloat()
    val screenWidth = (screen.bounds.size.width).toFloat()
    val screenHeight = (screen.bounds.size.height).toFloat()

    return object : PlatformConfiguration {
        override val screenWidth = screenWidth * density
        override val screenHeight = screenHeight * density
        override val density = density
    }
}

@Composable
fun ProvideIOSPlatformConfiguration(content: @Composable () -> Unit) {
    val platformConfiguration = providePlatformConfiguration()
    CompositionLocalProvider(
        LocalPlatformConfiguration provides platformConfiguration,
        content = content
    )
}