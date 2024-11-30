@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package org.propapel.prospeccion.selectSucursal.presentation.selectHome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.components.SelectSucursalDesktop
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.components.SelectSucursalMobile

@Composable
fun SelectSucursalScreenRoot(
    viewModel: SelectSucursalViewModel,
    onSucursalMove: (Int) -> Unit
){

    val state by viewModel.state.collectAsState()

    SelectSucursalScreen(
        state = state,
        onAction = {action ->
            when(action){
                is SelectSucursalAction.OnSucursalSelectedClick -> onSucursalMove(action.sucursalId)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun SelectSucursalScreen(
    state: SelectSucursalState,
    onAction: (SelectSucursalAction) -> Unit
) {
    val windowSizeClass = calculateWindowSizeClass()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = state.sucusalId)

    if (windowSizeClass.isMobile) {
        SelectSucursalMobile(
            state = state,
            onAction = onAction
        )
    } else {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        0f to PrimaryYellowLight,
                        0.6f to SoporteSaiBlue30,
                        1f to MaterialTheme.colorScheme.primary
                    )
                )
                .fillMaxSize()
                .padding(62.dp),
            contentAlignment = Alignment.Center
        ) {
            SelectSucursalDesktop(
                state = state,
                onAction = onAction,
                listState = listState
            )
        }
    }

    // Sincroniza el estado con el desplazamiento de la lista
    LaunchedEffect(state.sucusalId) {
        scope.launch {
            listState.animateScrollToItem(state.sucusalId)
        }
    }
}