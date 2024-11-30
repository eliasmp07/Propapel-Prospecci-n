package org.propapel.prospeccion.selectSucursal.presentation.dashboard

sealed interface DashboardGerenteAction {
    data object OnRefresh: DashboardGerenteAction
    data object OnRetryClick: DashboardGerenteAction
}