@file:OptIn(ExperimentalMaterialApi::class,
            ExperimentalMaterial3WindowSizeClassApi::class
)

package org.propapel.prospeccion.selectSucursal.presentation.dashboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.DrawableResource
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.components.DashboardGerenteDesktop
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.components.DashboardGerenteMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.merida
import prospeccion.composeapp.generated.resources.mexico
import prospeccion.composeapp.generated.resources.monterrey

@Composable
fun DashboardGerenteScreenRoot(
    user: AuthInfo,
    sucursalId: Int,
    viewModel: DashboardGerenteViewModel
) {

    val state by viewModel.state.collectAsState()

    DashboardGerenteScreen(
        state = state,
        onAction = {

        },
        user = user
    )
}

@Composable
private fun DashboardGerenteScreen(
    user: AuthInfo,
    state: DashboardGerenteState,
    onAction: (DashboardGerenteAction) -> Unit
) {

    val windowSizeClass = calculateWindowSizeClass()

    if(windowSizeClass.isMobile){
        DashboardGerenteMobile(
            user = user,
            state = state,
            onAction = onAction
        )
    }else{
        DashboardGerenteDesktop(
            state = state,
            onAction = onAction
        )
    }
}


fun getImageSucursal(user: AuthInfo): DrawableResource?{
    return when{
        user.sucursales.contains("Propapel Merida") -> Res.drawable.merida
        user.sucursales.contains("Propapel Monterrey") -> Res.drawable.monterrey
        user.sucursales.contains("Propapel Mexico") -> Res.drawable.mexico
        else -> null
    }
}
