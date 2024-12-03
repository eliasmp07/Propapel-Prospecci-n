package org.propapel.prospeccion.root.presentation.searchLead

import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer

data class SearchLeadSMState(
    val isLoadingScreen: Boolean = false,
    val products: List<Customer> = emptyList(),
    val query: String = "",
    val suggestion: List<SuggestionModel> = emptyList(),
    val isEmpty: Boolean = false,
    val error: UiText? = null,
    val isSearching: Boolean = false
)
