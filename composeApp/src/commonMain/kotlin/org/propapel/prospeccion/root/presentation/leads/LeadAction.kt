package org.propapel.prospeccion.root.presentation.leads

sealed interface LeadAction {
    data object OnAddLead : LeadAction
    data object OnRefresh: LeadAction
    data class OnDetailLeadClick(val id: String): LeadAction
    data object OnAddLeadClick: LeadAction
}