package org.propapel.prospeccion.root.presentation.searchLead

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

class SearchLeadSMViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private var _state = MutableStateFlow(SearchLeadSMState())
    val state: StateFlow<SearchLeadSMState> get() = _state.asStateFlow()

    fun onAction(action: SearchLeadSMAction) {
        when (action) {
            is SearchLeadSMAction.OnChangeQuery -> {
                _state.update { currentState ->
                    currentState.copy(
                        query = action.query
                    )
                }
            }
            SearchLeadSMAction.OnRetry -> searchProduct()
            SearchLeadSMAction.OnSearch -> searchProduct()
            else -> Unit
        }
    }

    private fun searchProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { currentState ->
                currentState.copy(
                    isSearching = true,
                    error = null
                )
            }

            val result = customerRepository.getMyCustomers()
            val query = state.value.query.lowercase().trim()

            _state.update { currentState ->
                when (result) {
                    is ResultExt.Error -> {
                        currentState.copy(
                            isSearching = false,
                            products = emptyList(),
                            error = result.error.asUiText()
                        )
                    }
                    is ResultExt.Success -> {
                        // Filtrar los clientes según la query
                        val filteredCustomers = result.data.filter { customer ->
                            customer.companyName.lowercase().contains(query)
                        }
                        currentState.copy(
                            isSearching = false,
                            isEmpty = filteredCustomers.isEmpty(),
                            products = filteredCustomers,
                            error = null
                        )
                    }
                }
            }
        }
    }
}