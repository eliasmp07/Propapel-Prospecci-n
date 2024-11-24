package org.propapel.prospeccion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import org.koin.compose.getKoin
import org.propapel.prospeccion.core.presentation.designsystem.components.ProvideIOSPlatformConfiguration
import org.propapel.prospeccion.di.initKoin
import org.propapel.prospeccion.navigation.RootGraph

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin {  }
    }
){
    val viewModel:AppleMainViewModel = getKoin().get()
    ProvideIOSPlatformConfiguration {
        // En tu UI
        if(!viewModel.state.isCheckingAuth) {
            RootGraph(isLogging = viewModel.state.isLoggedIn)
        }
    }
}