package org.propapel.prospeccion.gerentePanel.presentation.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardGerenteViewModel(): ViewModel() {
    private val _state = MutableStateFlow(DashboardGerenteState())
    val state: StateFlow<DashboardGerenteState> get() =_state.asStateFlow()
}