@file:OptIn(KoinExperimentalAPI::class)

package org.propapel.prospeccion.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.propapel.prospeccion.navigation.utils.Destination
import org.propapel.prospeccion.root.presentation.addlead.AddLeadScreenRoot
import org.propapel.prospeccion.root.presentation.addlead.AddLeadViewModel
import org.propapel.prospeccion.root.presentation.completeReminder.CompleteReminderScreenRoot
import org.propapel.prospeccion.root.presentation.completeReminder.CompleteReminderViewModel
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionLeadScreeRoot
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionViewModel
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderScreenRoot
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderViewModel
import org.propapel.prospeccion.root.presentation.detailLead.DetailCustomerSMScreenRoot
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadViewModel
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.DetailReminderCustomerScreenRoot
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.DetailReminderCustomerViewModel
import org.propapel.prospeccion.root.presentation.homeRoot.HomeRootViewModel
import org.propapel.prospeccion.root.presentation.homeRoot.HomeScreen
import org.propapel.prospeccion.root.presentation.searchLead.SearchLeadSMScreenRoot
import org.propapel.prospeccion.root.presentation.searchLead.SearchLeadSMViewModel
import org.propapel.prospeccion.root.presentation.updateCustomer.UpdateLeadScreenRoot
import org.propapel.prospeccion.root.presentation.updateCustomer.UpdateLeadViewModel
import org.propapel.prospeccion.root.presentation.updateProfile.UpdateProfileSMSScreenRoot
import org.propapel.prospeccion.root.presentation.updateProfile.UpdateProfileSMViewModel

fun NavGraphBuilder.proSales(
    navController: NavHostController
) {
    navigation<Destination.ProSales>(
        startDestination = Destination.DashBoard
    ) {
        composable<Destination.DashBoard> {
            val viewModel = koinViewModel<HomeRootViewModel>()
            HomeScreen(
                viewModel = viewModel,
                onDarkTheme = {

                },
                onAddLead = {
                    navController.navigate(Destination.AddLead)
                },
                onCreateReminder = {
                    navController.navigate(Destination.CreateReminder)
                },
                onUpdateProfile = {
                    navController.navigate(Destination.EditProfile)
                },
                onLogout = {
                    navController.navigate(Destination.AuthGraph) {
                        popUpTo(Destination.ProSales) {
                            inclusive = false
                        }
                    }
                },
                onDetailLead = {
                    navController.navigate(Destination.DetailCustomer(it))
                },
                onDetailReminderCustomer = {
                    navController.navigate(Destination.DetailReminderCustomer(it))
                },
                onSearchLead = {
                    navController.navigate(Destination.SearchLead)
                },
                onUpdateLead = {
                    navController.navigate(Destination.UpdateLead(it))
                }
            )
        }

        composable<Destination.CreateReminder> {
            val viewModel = koinViewModel<CreateReminderViewModel>()
            LaunchedEffect(Unit) {
                viewModel.getAllMyCustomers()
            }
            CreateReminderScreenRoot(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Destination.AddLead> {
            val viewModel = koinViewModel<AddLeadViewModel>()
            AddLeadScreenRoot(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Destination.SearchLead> {
            val viewModel = koinViewModel<SearchLeadSMViewModel>()
            SearchLeadSMScreenRoot(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                },
                onDetailCustomer = {
                    navController.navigate(Destination.DetailCustomer(it))
                })
        }

        composable<Destination.DetailCustomer>(
        ) { navBackEntry ->

            val viewModel = koinViewModel<DetailLeadViewModel>()
            val detailCustomer = navBackEntry.toRoute<Destination.DetailCustomer>()
            LaunchedEffect(Unit) {
                viewModel.getCustomerById(detailCustomer.idCustomer.toInt())
            }
            DetailCustomerSMScreenRoot(
                viewModel = viewModel,
                onUpdateCustomer = {
                    navController.navigate(Destination.UpdateLead(it))
                },
                onDetailReminderLead = {
                    navController.navigate(Destination.DetailReminderCustomer(it))
                },
                onAddInteractions = {
                    navController.navigate(Destination.CreateInteraction(it))
                },
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Destination.CompleteReminder> {
            val viewModel = koinViewModel<CompleteReminderViewModel>()
            CompleteReminderScreenRoot(
                viewModel = viewModel
            )
        }

        composable<Destination.DetailReminderCustomer>(
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(600),
                    initialOffsetY = { fullHeight -> fullHeight }
                ) + fadeIn(
                    animationSpec = tween(600)
                )
            }
        ) { navBackEntry ->
            val detailReminder = navBackEntry.toRoute<Destination.DetailReminderCustomer>()
            val vievModel = koinViewModel<DetailReminderCustomerViewModel>()

            LaunchedEffect(Unit) {
                vievModel.getDayReminder(detailReminder.idReminder.toInt())
            }
            DetailReminderCustomerScreenRoot(
                viewModel = vievModel,
                onCompleteReminder = {
                    navController.navigate(Destination.CompleteReminder(it))
                },
                onBack = {
                    navController.navigateUp()
                }
            )

        }

        composable<Destination.UpdateLead> { navBackEntry ->
            val customerUpdate = navBackEntry.toRoute<Destination.UpdateLead>()
            val viewModel = koinViewModel<UpdateLeadViewModel>()
            LaunchedEffect(Unit) {
                viewModel.getLead(customerUpdate.customerId)
            }
            UpdateLeadScreenRoot(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Destination.EditProfile> {
            val viewModel = koinViewModel<UpdateProfileSMViewModel>()
            UpdateProfileSMSScreenRoot(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Destination.CreateInteraction> { navBackStackEntry ->
            val viewModel = koinViewModel<CreateInteractionViewModel>()
            val customerId = navBackStackEntry.toRoute<Destination.CreateInteraction>().customerId

            LaunchedEffect(Unit) {
                viewModel.onChangeIdCustomer(customerId)
            }

            CreateInteractionLeadScreeRoot(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }

    }
}