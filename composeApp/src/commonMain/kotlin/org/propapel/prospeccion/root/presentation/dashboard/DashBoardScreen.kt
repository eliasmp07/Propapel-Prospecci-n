@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package org.propapel.prospeccion.root.presentation.dashboard

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
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
    onMoveLeadScreen: () -> Unit,
    onUpdateProfile: () -> Unit,
    onLogout: () -> Unit,
    onWebView : (String) -> Unit,
    onCreateReminder: () -> Unit,
    onSearchLead: () -> Unit,
    user: AuthInfo,
    windowSizeClass: WindowSizeClass
) {
    val state by viewModel.state.collectAsState()
    DashboardScreen(
        state = state,
        user,
        windowSizeClass = windowSizeClass,
        onAction = { action ->
            when (action) {
                DashboardSMAction.OnUpdateProfile -> onUpdateProfile()
                DashboardSMAction.OnLogout -> onLogout()
                is DashboardSMAction.OnWebViewClick -> onWebView(action.url)
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
    if (windowSizeClass.isMobile) {
        DashboardScreenMobile(
            state = state,
            user = user,
            onAction = onAction
        )
    } else {
        DashboardScreenWindows(
            state = state,
            user = user,
            onAction = onAction
        )
    }

}


val WindowSizeClass.isMobile: Boolean
    get() = this.widthSizeClass == WindowWidthSizeClass.Compact