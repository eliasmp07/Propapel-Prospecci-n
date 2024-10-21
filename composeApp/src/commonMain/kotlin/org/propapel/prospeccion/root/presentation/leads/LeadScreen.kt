package org.propapel.prospeccion.root.presentation.leads

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.core.presentation.designsystem.components.handleResultView
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.leads.components.desktop.LeadScreenDesktop
import org.propapel.prospeccion.root.presentation.leads.components.mobile.LeadScreenMobile

@Composable
fun LeadScreenRoot(
    viewModel: LeadSMViewModel,
    windowSizeClass: WindowSizeClass,
    onAddLead : () -> Unit
){
    val state by viewModel.state.collectAsState()
    LeadScreen(
        windowSizeClass = windowSizeClass,
        onAction = {action ->
            when(action){
                LeadAction.OnAddLead -> {
                    onAddLead()
                }
                LeadAction.OnAddLeadClick -> {
                    onAddLead()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        },
        onAddLead = onAddLead,
        state = state
    )
}

@Composable
private fun LeadScreen(
    state: LeadSMState,
    windowSizeClass: WindowSizeClass,
    onAction : (LeadAction) -> Unit,
    onAddLead :() -> Unit
){
    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact){
        LeadScreenMobile(
            onRefresh = {
                onAction(LeadAction.OnRefresh)
            },
            state = state,
            onAction = onAction
        )
    }else{
        LeadScreenDesktop(
            onAddLead = onAddLead
        )
    }
}