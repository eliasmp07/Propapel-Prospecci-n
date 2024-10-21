package org.propapel.prospeccion.root.presentation.leads

import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer

data class LeadSMState(
    val customers: List<Customer> = listOf(),
    val isRefreshing: Boolean = false,
    val error: UiText? = null
)
