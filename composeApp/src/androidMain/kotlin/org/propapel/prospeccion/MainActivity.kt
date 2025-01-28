package org.propapel.prospeccion

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.dowloadapk.DownloadApk
import org.propapel.prospeccion.navigation.RootGraph
import org.propapel.prospeccion.notifications.utils.HomeAskPermission
import org.propapel.prospeccion.notifications.utils.MultiPermission


class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }
        setContent {
            val blockVersion by viewModel.blockVersion.collectAsState()
            val linkVersion by viewModel.linkVersion.collectAsState()
            val versionNew by viewModel.newVersion.collectAsState()
            if (!blockVersion) {
                val context = LocalContext.current
                UpdateAppScreen(
                    onUpdate = {
                        val downloadApk = DownloadApk(this@MainActivity)
                        // For starting download call the method startDownloadingApk() by passing the URL and the optional filename
                        downloadApk.startDownloadingApk(
                            linkVersion,
                            versionNew
                        )
                    },
                    onCloseApp = {
                        this.finish()
                    }
                )
            }else{
                if (!viewModel.state.isCheckingAuth) {
                    App(
                        content = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                HomeAskPermission(permission = Manifest.permission.POST_NOTIFICATIONS)
                            }
                            MultiPermission(
                                permissions = listOf(
                                    Manifest.permission.REQUEST_INSTALL_PACKAGES,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                )
                            )

                            val view = LocalView.current
                            if (!view.isInEditMode) {
                                SideEffect {
                                    val windows = (view.context as Activity).window
                                    WindowCompat.getInsetsController(
                                        windows,
                                        view
                                    ).isAppearanceLightStatusBars = true
                                }
                            }
                            RootGraph(
                                isLogging = viewModel.state.isLoggedIn,
                                isManager = viewModel.state.isManager
                            )
                        }
                    )

                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.limitFromScale())
    }
}

fun Context.limitFromScale(
    maxFontScale: Float = 1.4F,
    maxDpiScale: Float = 1.1F
): Context {
    val configuration = resources.configuration
    val defaultDeviceDensity = DisplayMetrics.DENSITY_DEVICE_STABLE
    val maximumDpiScale = (defaultDeviceDensity * maxDpiScale).toInt()
    val exceedsFontScale = configuration.fontScale > maxFontScale
    val exceedsDpiScale = configuration.densityDpi > maximumDpiScale

    configuration.apply {
        if (exceedsFontScale) {
            fontScale = maxFontScale
        }
        if (exceedsDpiScale) {
            densityDpi = maximumDpiScale
        }
    }
    return if (exceedsFontScale || exceedsDpiScale) createConfigurationContext(configuration) else this
}

@Composable
fun UpdateAppScreen(
    onUpdate: () -> Unit,
    onCloseApp: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        ).padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nueva version disponible!!!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.update_available),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
        Text(
            text = "Instala la nueva version para gozar de las nuevas caracteristicas",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
        ProSalesActionButton(
            text = "Descargar",
            textColor = Color.White
        ) {
            onUpdate()
        }
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ProSalesActionButtonOutline(
            text = "Cerrar app"
        ) {
            onCloseApp()
        }
    }
}