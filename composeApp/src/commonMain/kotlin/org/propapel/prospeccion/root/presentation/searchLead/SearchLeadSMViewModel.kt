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
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.repository.CustomerRepository

class SearchLeadSMViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoadingScreen = true
                )
            }
            val result = customerRepository.getMyCustomers()

            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            products = listOf()
                        )
                    }
                }
                is ResultExt.Success -> {
                    val sugestions: MutableList<SuggestionModel> = mutableListOf()
                    var interator = 6

                    // Iteramos sobre la lista de clientes
                    result.data.forEach { customer ->
                        if (customer.progressLead >= 40.00 && interator > 0) {
                            sugestions.add(
                                SuggestionModel(
                                    tag = customer.companyName
                                )
                            )
                            interator--
                        }
                    }

                    _state.update {
                        it.copy(
                            isLoadingScreen = false,
                            suggestion = sugestions.toList(),
                            products = result.data
                        )
                    }
                }
            }
        }
    }

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

    fun getCustomer(query: String): List<Customer>{
        val filteredList = linkedSetOf<Customer>()

        _state.value.products.forEach { customer ->

            if (customer.companyName.contains(query, ignoreCase = true)) {
                filteredList.add(customer)
            }

            if (customer.contactName.contains(query, ignoreCase = true)) {
                filteredList.add(customer)
            }

            customer.tags.forEach {
                if (it.contains(query, ignoreCase = true)) {
                    filteredList.add(customer)
                }
            }
        }
        return filteredList.toList()
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
                        val newList = result.data.toMutableList()

                        newList.sortBy {
                            it.companyName.uppercase()
                        }
                        // Filtrar los clientes según la query
                        val filteredCustomers = newList
                            .filter { customer ->
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