package org.propapel.prospeccion.selectSucursal.presentation.dashboard

import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.presentation.leads.UiState
import org.propapel.prospeccion.selectSucursal.domain.model.CustomerUser
import org.propapel.prospeccion.selectSucursal.domain.model.ProjectUser
import org.propapel.prospeccion.selectSucursal.domain.model.UserItem

data class DashboardGerenteState(
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val customersUser: UiState<List<CustomerUser>> = UiState.Loading(),
    val projectsCustomer: UiState<List<ProjectUser>> = UiState.Loading(),
    val error: UiText? = null,
    val users: UiState<List<UserItem>> = UiState.Loading()
)
