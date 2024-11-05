package org.propapel.prospeccion.root.presentation.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.propapel.prospeccion.root.presentation.account.AccountSMViewModel
import org.propapel.prospeccion.root.presentation.addlead.AddLeadViewModel
import org.propapel.prospeccion.root.presentation.completeReminder.CompleteReminderViewModel
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionViewModel
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderViewModel
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMViewModel
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadViewModel
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.DetailReminderCustomerViewModel
import org.propapel.prospeccion.root.presentation.homeRoot.HomeRootViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadSMViewModel
import org.propapel.prospeccion.root.presentation.searchLead.SearchLeadSMViewModel
import org.propapel.prospeccion.root.presentation.updateCustomer.UpdateLeadViewModel
import org.propapel.prospeccion.root.presentation.updateProfile.UpdateProfileSMViewModel
import org.propapel.prospeccion.root.presentation.users.UserSMViewModel

val proSalesPresentationModule = module {
    viewModelOf(::HomeRootViewModel)
    viewModelOf(::AddLeadViewModel)
    viewModelOf(::UpdateLeadViewModel)
    viewModelOf(::CreateReminderViewModel)
    viewModelOf(::CompleteReminderViewModel)
    viewModelOf(::DetailLeadViewModel)
    viewModelOf(::SearchLeadSMViewModel)
    viewModelOf(::DetailReminderCustomerViewModel)
    viewModelOf(::AccountSMViewModel)
    viewModelOf(::CreateInteractionViewModel)
    viewModelOf(::UpdateProfileSMViewModel)
    viewModelOf(::LeadSMViewModel)
    viewModelOf(::UserSMViewModel)
    viewModelOf(::DashboardSMViewModel)
}