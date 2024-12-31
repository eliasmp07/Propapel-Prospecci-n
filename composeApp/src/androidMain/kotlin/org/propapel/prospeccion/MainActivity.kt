package org.propapel.prospeccion


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Devices.DESKTOP
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.propapel.prospeccion.core.presentation.designsystem.components.ProvideAndroidPlatformConfiguration
import org.propapel.prospeccion.navigation.RootGraph
import org.propapel.prospeccion.notifications.utils.HomeAskPermission
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalScreen
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalState
import java.io.File

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
            if(!viewModel.state.isCheckingAuth) {
                ProvideAndroidPlatformConfiguration {
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

fun Context.limitFromScale(maxFontScale: Float = 1.4F, maxDpiScale: Float = 1.1F): Context{
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

/*@Composable
fun UpdateScreen(viewModel: MainViewModel, context: Context) {
    val isUpdateAvailable by viewModel.isUpdateAvailable.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()

    LaunchedEffect(downloadProgress) {
        if (downloadProgress == 100) {
            // Instalar el APK desde el archivo en caché
            installApk(context, viewModel.getApkFile())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isUpdateAvailable) {
            Text(text = "Nueva actualización disponible")
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(progress = downloadProgress / 100f)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Progreso de descarga: $downloadProgress%")
        } else {
            Text(text = "No hay actualizaciones disponibles")
        }
    }
}


 */

fun installApk(context: Context, apkFile: File?) {
    apkFile?.let {
        val apkUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", it)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(apkUri, "application/vnd.android.package-archive")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)
    }
}
