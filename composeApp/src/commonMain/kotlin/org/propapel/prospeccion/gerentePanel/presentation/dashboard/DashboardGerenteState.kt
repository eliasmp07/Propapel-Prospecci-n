package org.propapel.prospeccion.gerentePanel.presentation.dashboard

import org.propapel.prospeccion.core.presentation.ui.UiText

data class DashboardGerenteState(
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiText? = null,

)
