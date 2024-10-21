package org.propapel.prospeccion.root.presentation.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.propapel.prospeccion.root.presentation.account.AccountSMViewModel
import org.propapel.prospeccion.root.presentation.addlead.AddLeadViewModel
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMViewModel
import org.propapel.prospeccion.root.presentation.homeRoot.HomeRootViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadSMViewModel
import org.propapel.prospeccion.root.presentation.updateProfile.UpdateProfileSMViewModel
import org.propapel.prospeccion.root.presentation.users.UserSMViewModel

val proSalesPresentationModule = module {
    viewModelOf(::HomeRootViewModel)
    viewModelOf(::AddLeadViewModel)
    viewModelOf(::AccountSMViewModel)
    viewModelOf(::UpdateProfileSMViewModel)
    viewModelOf(::LeadSMViewModel)
    viewModelOf(::UserSMViewModel)
    viewModelOf(::DashboardSMViewModel)
}