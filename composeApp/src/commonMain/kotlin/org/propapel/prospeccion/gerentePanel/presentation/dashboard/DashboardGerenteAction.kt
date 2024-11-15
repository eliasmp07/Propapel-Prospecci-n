package org.propapel.prospeccion.gerentePanel.presentation.dashboard

sealed interface DashboardGerenteAction {
    data object OnRefresh: DashboardGerenteAction
    data object OnRetryClick: DashboardGerenteAction
}