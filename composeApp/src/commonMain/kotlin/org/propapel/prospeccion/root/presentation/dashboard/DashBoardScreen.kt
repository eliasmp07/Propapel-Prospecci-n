package org.propapel.prospeccion.root.presentation.dashboard

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.root.presentation.dashboard.components.desktop.DashboardScreenWindows
import org.propapel.prospeccion.root.presentation.dashboard.components.mobile.DashboardScreenMobile

@Composable
fun DashBoardScreenRoot(
    viewModel: DashboardSMViewModel,
    onDetailReminderCustomer: (String) -> Unit,
    onMoveLeadScreen:() -> Unit,
    onCreateReminder: () -> Unit,
    onSearchLead: () -> Unit,
    user: AuthInfo,
    windowSizeClass: WindowSizeClass
){
    val state by viewModel.state.collectAsState()
    DashboardScreen(
        state = state,user,
        windowSizeClass = windowSizeClass,
        onAction = {action ->
            when(action){
                DashboardSMAction.OnCreateReminderClick -> onCreateReminder()
                is DashboardSMAction.OnDetailReminderCustomer -> {
                    onDetailReminderCustomer(action.idReminder)
                }
                DashboardSMAction.OnSearchLead -> onSearchLead()
                is DashboardSMAction.OnMoveLeadScreenClick -> onMoveLeadScreen()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun DashboardScreen(
    state: DashboardSMState,
    user: AuthInfo,
    windowSizeClass: WindowSizeClass,
    onAction: (DashboardSMAction) -> Unit,
) {
    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact){
        DashboardScreenMobile(state = state, user = user, onAction = onAction )
    }else{
        DashboardScreenWindows()
    }

}