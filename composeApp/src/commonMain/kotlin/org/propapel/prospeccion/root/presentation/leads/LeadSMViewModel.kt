package org.propapel.prospeccion.root.presentation.leads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.presentation.ui.asUiText
import org.propapel.prospeccion.root.domain.repository.CustomerRepository

class LeadSMViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private var _state = MutableStateFlow(LeadSMState())
    val state: StateFlow<LeadSMState> get() = _state.asStateFlow()

    init {
        onRefresh()
    }

    fun onAction(
        action: LeadAction
    ) {
        when (action) {
            LeadAction.OnRefresh -> onRefresh()
            else -> Unit
        }
    }

    private fun onRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isRefreshing = true, error = null)
            }
            when (val result = customerRepository.getMyCustomers()) {
                is ResultExt.Error -> {
                    _state.update { it.copy(customers = listOf(), isRefreshing = false, error = result.error.asUiText()) }
                }

                is ResultExt.Success -> {

                    _state.update { it.copy(customers = result.data, isRefreshing = false, error = null) }
                }
            }
        }
    }
}