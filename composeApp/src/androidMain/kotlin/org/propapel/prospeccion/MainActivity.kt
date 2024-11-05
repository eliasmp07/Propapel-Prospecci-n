package org.propapel.prospeccion

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.propapel.prospeccion.navigation.RootGraph
import org.propapel.prospeccion.notifications.utils.HomeAskPermission

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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            HomeAskPermission(permission = Manifest.permission.POST_NOTIFICATIONS)
                        }
                        val  view = LocalView.current
                        if (!view.isInEditMode){
                            SideEffect {
                                val windows = (view.context as Activity).window
                                WindowCompat.getInsetsController(windows, view).isAppearanceLightStatusBars = true
                            }
                        }
                        RootGraph(
                            isLogging = viewModel.state.isLoggedIn
                        )
                    }
                )
            }

        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.limitFromScale())
    }
}

fun Context.limitFromScale(maxFontScale: Float = 1.1F, maxDpiScale: Float = 0.9F): Context{
    val configuration = resources.configuration
    val defaultDeviceDensity = DisplayMetrics.DENSITY_DEVICE_STABLE
    val maximumDpiScale = (defaultDeviceDensity * maxDpiScale).toInt()
    val exceedsFontScale = configuration.fontScale > maxFontScale
    val exceedsDpiScale = configuration.densityDpi > maximumDpiScale

    configuration.apply {
        if (exceedsFontScale){
            fontScale = maxFontScale
        }
        if (exceedsDpiScale){
            densityDpi = maximumDpiScale
        }
    }
    return  if(exceedsFontScale || exceedsDpiScale) createConfigurationContext(configuration) else this
}
