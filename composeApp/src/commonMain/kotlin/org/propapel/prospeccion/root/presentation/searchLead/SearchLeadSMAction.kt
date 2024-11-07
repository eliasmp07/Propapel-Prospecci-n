package org.propapel.prospeccion.root.presentation.searchLead

sealed interface SearchLeadSMAction {
    data object OnBack : SearchLeadSMAction
    data object OnSearch :SearchLeadSMAction
    data object OnRetry: SearchLeadSMAction
    data class OnChangeQuery(val query: String): SearchLeadSMAction
    data class OnCustomerDetailClick(val customerId: String): SearchLeadSMAction
}