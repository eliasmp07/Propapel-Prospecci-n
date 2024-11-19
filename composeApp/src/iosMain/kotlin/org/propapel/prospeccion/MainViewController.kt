package org.propapel.prospeccion

import androidx.compose.ui.window.ComposeUIViewController
import org.propapel.prospeccion.core.presentation.designsystem.components.ProvideIOSPlatformConfiguration

fun MainViewController() = ComposeUIViewController {
    ProvideIOSPlatformConfiguration {
        App()
    }
}