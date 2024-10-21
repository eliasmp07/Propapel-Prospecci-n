package org.propapel.prospeccion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin
import org.propapel.prospeccion.di.initKoin
import org.propapel.prospeccion.navigation.RootGraph
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo
import java.awt.Dimension

fun main() = application {


    var theme by remember {
        mutableStateOf(false)
    }

    initKoin()

    //Variable que obtiene el viewmodel
    val viewModel: MainDesktopViewModel = getKoin().get()

    Window(
        state = rememberWindowState(
            size = DpSize(width = 1280.dp, height = 720.dp),
            position = WindowPosition(Alignment.Center),
        ),
        icon = painterResource(Res.drawable.logo),
        onCloseRequest = ::exitApplication,
        title = "ProSales"
    ) {
        window.minimumSize = Dimension(350, 600)
        App(
            darkTheme = theme,
            content = {
                RootGraph(
                    isLogging = viewModel.state.isLoggedIn
                )
            }
        )
    }
}