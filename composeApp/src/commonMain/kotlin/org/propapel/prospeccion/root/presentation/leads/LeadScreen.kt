package org.propapel.prospeccion.root.presentation.leads

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.propapel.prospeccion.root.presentation.leads.components.desktop.LeadScreenDesktop
import org.propapel.prospeccion.root.presentation.leads.components.mobile.LeadScreenMobile

@Composable
fun LeadScreenRoot(
    viewModel: LeadSMViewModel,
    windowSizeClass: WindowSizeClass,
    onUpdateLead: (String) -> Unit,
    onDetailLead: (String) -> Unit,
    onSearchLead: () -> Unit,
    onAddLead : () -> Unit
){
    val state by viewModel.state.collectAsState()
    LeadScreen(
        windowSizeClass = windowSizeClass,
        onAction = {action ->
            when(action){
                is LeadAction.OnDetailLeadClick-> onDetailLead(action.leadId)
                LeadAction.OnAddLead -> {
                    onAddLead()
                }
                is LeadAction.OnUpdateLeadClick -> onUpdateLead(action.leadId)
                LeadAction.OnSearchCustomerClick -> onSearchLead()
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