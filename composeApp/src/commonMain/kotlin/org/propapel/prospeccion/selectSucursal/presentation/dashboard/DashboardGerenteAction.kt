package org.propapel.prospeccion.selectSucursal.presentation.dashboard

sealed interface DashboardGerenteAction {
    data object OnRefresh: DashboardGerenteAction
    data object OnAddLead: DashboardGerenteAction
    data object OnSearchLead: DashboardGerenteAction
    data object OnLogout: DashboardGerenteAction
    data object OnEditProfile: DashboardGerenteAction
    data object OnRetryClick: DashboardGerenteAction
}