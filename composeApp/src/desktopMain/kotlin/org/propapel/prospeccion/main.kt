package org.propapel.prospeccion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowDecoration
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin
import org.propapel.prospeccion.core.presentation.designsystem.components.ProvideDesktopPlatformConfiguration
import org.propapel.prospeccion.di.initKoin
import org.propapel.prospeccion.navigation.RootGraph
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo
import java.awt.Dimension

fun main(){
    initKoin()

    val viewModel: MainDesktopViewModel = getKoin().get()

    application {
        var showSplashScreen by remember { mutableStateOf(true) }

        Window(
            state = rememberWindowState(
                placement = WindowPlacement.Maximized,
                position = WindowPosition(Alignment.Center)
            ),
            decoration = WindowDecoration.SystemDefault,
            icon = painterResource(Res.drawable.logo),
            onCloseRequest = ::exitApplication,
            title = "ProSales"
        ) {
            window.minimumSize = Dimension(350, 600)

            Crossfade(
                targetState = showSplashScreen
            ){tarjetValue ->
                if(tarjetValue){
                    SplashScreen {
                        showSplashScreen = false
                    }
                }else{
                    ProvideDesktopPlatformConfiguration {
                        App(
                            content = {
                                RootGraph(
                                    isLogging = viewModel.state.isLoggedIn,
                                    isManager = viewModel.state.isManager,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    var progress by remember { mutableStateOf(0f) }

    // Animación para el progreso del indicador
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 2000)
    )

    // Pantalla de presentación con indicador de progreso
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().align(Alignment.Center)
        ) {
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Image(
                modifier = Modifier.size(DpSize(100.dp, 100.dp)),
                painter = painterResource(Res.drawable.logo),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(30.dp))
            CircularProgressIndicator(
                progress = {
                    animatedProgress
                },
                trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "© 2024 Propapel.\n Todos los derechos reservados.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // Retraso simulado de 2 segundos para el splash screen
    LaunchedEffect(Unit) {
        progress = 1f // Llenado del indicador en 2 segundos
        delay(2000)
        onTimeout() // Cambia a la pantalla principal
    }
}