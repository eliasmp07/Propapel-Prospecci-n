@file:OptIn(ExperimentalAnimationApi::class)

package org.propapel.prospeccion

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowDecoration
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin
import org.propapel.prospeccion.core.presentation.designsystem.components.ProvideDesktopPlatformConfiguration
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateAttentionRepeat
import org.propapel.prospeccion.di.initKoin
import org.propapel.prospeccion.navigation.RootGraph
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo
import java.awt.Dimension
import java.io.File
import kotlin.math.max

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

            var restartRequired by remember { mutableStateOf(false) }
            var downloading by remember { mutableStateOf(0F) }
            var initialized by remember { mutableStateOf(false) }
            val download: KCEFBuilder.Download = remember { KCEFBuilder.Download.Builder().github().build() }

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    KCEF.init(builder = {
                        installDir(File("kcef-bundle"))

                        /*
                          Add this code when using JDK 17.
                          Builder().github {
                              release("jbr-release-17.0.10b1087.23")
                          }.buffer(download.bufferSize).build()
                         */
                        progress {
                            onDownloading {
                                downloading = max(it, 0F)
                            }
                            onInitialized {
                                initialized = true
                            }
                        }
                        settings {
                            cachePath = File("cache").absolutePath
                        }
                    }, onError = {
                        it?.printStackTrace()
                    }, onRestartRequired = {
                        restartRequired = true
                    })
                }
            }
            if (restartRequired) {
                Text(text = "Restart required.")
            } else {
                Crossfade(
                    targetState = initialized
                ){ initialScreen ->
                    if (initialScreen){
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
                    }else{

                        DownloadingScreen(downloading)
                    }
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    KCEF.disposeBlocking()
                }
            }
        }
    }
}

@Composable
fun DownloadingScreen(
    downloading: Float,
) {
    // Variable que recuerda el mensaje actual basado en el progreso
    val currentMessage = remember(downloading.toInt()) {
        when (downloading.toInt()) {
            in 0..30 -> "Estamos descargando algunas cosas, por favor espere a que se termine."
            in 31..80 -> "La descarga está progresando. Espere un poco más."
            in 80..99 -> "Ya estamos por terminar. Espere un poco más."
            else -> ""
        }
    }
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val float by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color"
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(Res.drawable.logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(100.dp))
        AnimatedContent(
            targetState = currentMessage,
            transitionSpec = {
                if (targetState != initialState) {
                    (slideInVertically { it } + fadeIn()).togetherWith(slideOutVertically { -it } + fadeOut())
                } else {
                    (slideInVertically { -it } + fadeIn()).togetherWith(slideOutVertically { it } + fadeOut())
                }.using(SizeTransform(clip = false))
            },
            label = "animated content"
        ) { message ->
            Text(
                modifier = Modifier,
                text = message,
                color = Color.Black.copy(
                    alpha = float
                ),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(1f))
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