package org.propapel.prospeccion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.propapel.prospeccion.navigation.RootGraph

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }
        setContent {
            if(!viewModel.state.isCheckingAuth) {
                App(
                    content = {
                        RootGraph(
                            isLogging = viewModel.state.isLoggedIn
                        )
                    }
                )
            }

        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}