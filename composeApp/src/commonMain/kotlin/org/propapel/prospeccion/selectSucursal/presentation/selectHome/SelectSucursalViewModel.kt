package org.propapel.prospeccion.selectSucursal.presentation.selectHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.selectSucursal.domain.repository.SucursalesRepository

class SelectSucursalViewModel(
    private val sessionStorage: SessionStorage,
    private val sucursalesRepository: SucursalesRepository
): ViewModel() {

    private val _state = MutableStateFlow(SelectSucursalState())
    val state: StateFlow<SelectSucursalState> get() = _state.asStateFlow()

    init {
        getSucursales()
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    authInfo = sessionStorage.get()?: AuthInfo()
                )
            }
        }
    }

    private fun getSucursales(){
        viewModelScope.launch(Dispatchers.IO){
            val result = sucursalesRepository.getAllSucursales()

            when(result){
                is ResultExt.Error ->{
                    _state.update {
                        it.copy(
                            sucusales = emptyList()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            sucusales = result.data
                        )
                    }
                }
            }
        }
    }
    fun onAction(action: SelectSucursalAction) {
        when (action) {
            SelectSucursalAction.OnLeftSelectedClick -> {
                _state.update {currentState ->
                    val newId = (currentState.sucusalId - 1).coerceAtLeast(1)
                    currentState.copy(sucusalId = newId)
                }

            }
            SelectSucursalAction.OnRightSelectedClick -> {
                _state.update {currentState ->
                    val newId = (currentState.sucusalId + 1).coerceAtMost(currentState.sucusales.lastIndex + 1)
                    currentState.copy(sucusalId = newId)
                }

            }
            is SelectSucursalAction.OnSucusalChange -> {
                _state.update { currentState ->
                    if(currentState.sucusalId == action.sucursalId){
                        currentState.copy(
                            sucusalId = 0
                        )
                    }else{
                        currentState.copy(
                            sucusalId = action.sucursalId
                        )
                    }
                }

            }
            else -> Unit
        }
    }
}