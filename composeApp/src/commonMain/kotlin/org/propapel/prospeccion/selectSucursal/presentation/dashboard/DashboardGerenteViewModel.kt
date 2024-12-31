package org.propapel.prospeccion.selectSucursal.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.presentation.ui.toImageAndTextError
import org.propapel.prospeccion.root.presentation.leads.UiState
import org.propapel.prospeccion.root.presentation.leads.toState
import org.propapel.prospeccion.selectSucursal.domain.model.Sucursale
import org.propapel.prospeccion.selectSucursal.domain.repository.SucursalesRepository
import org.propapel.prospeccion.selectSucursal.domain.repository.UserRepository

class DashboardGerenteViewModel(
    private val userRepository: UserRepository,
    private val sessionStorage: SessionStorage,
    private val sucursalesRepository: SucursalesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardGerenteState())
    val state: StateFlow<DashboardGerenteState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sessionStorage.getUserFlow().collectLatest { user ->
                _state.update {
                    it.copy(
                        user = user?: AuthInfo()
                    )
                }
            }

        }
    }

    fun getSucursal(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = sucursalesRepository.getSucursalById(id)

            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            sucursal = UiState.Error(result.error.toImageAndTextError())
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            sucursal = UiState.Success(result.data)
                        )
                    }
                }
            }
        }
    }

    fun getUserBySucursal(sucursalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.getAllUsersBySucursal(sucursalId)

            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            customersUser = UiState.Error(result.error.toImageAndTextError()),
                            users = UiState.Error(result.error.toImageAndTextError())
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update { it.copy(users = result.data.toState()) }

                    // Manejo de estado para los clientes
                    val customers = result.data.flatMap { it.customers }
                    _state.update { it.copy(customersUser = customers.toState()) }

                    // Manejo de estado para los proyectos
                    val projects = customers.flatMap { it.projects }
                    _state.update { it.copy(projectsCustomer = projects.toState(), leadsBySucursal = customers.size, projectsBySucursal = projects.size) }

                }
            }
        }
    }
}
