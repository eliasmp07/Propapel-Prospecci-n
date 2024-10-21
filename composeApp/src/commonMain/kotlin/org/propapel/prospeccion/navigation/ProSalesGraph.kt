@file:OptIn(KoinExperimentalAPI::class)

package org.propapel.prospeccion.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.lifecycle.viewmodel.compose.viewModel
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
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.DetailReminderCustomerScreenRoot
import org.propapel.prospeccion.root.presentation.homeRoot.HomeRootViewModel
import org.propapel.prospeccion.root.presentation.homeRoot.HomeScreen
import org.propapel.prospeccion.root.presentation.updateProfile.UpdateProfileSMSScreenRoot
import org.propapel.prospeccion.root.presentation.updateProfile.UpdateProfileSMViewModel

fun NavGraphBuilder.proSales(
    navController: NavHostController
){
    navigation<Destination.ProSales>(
        startDestination = Destination.DashBoard
    ){
        composable<Destination.DashBoard>{
            val viewModel = koinViewModel<HomeRootViewModel>()
            HomeScreen(
                viewModel = viewModel,
                onDarkTheme = {

                },
                onAddLead ={
                    navController.navigate(Destination.AddLead)
                },
                onUpdateProfile = {
                    navController.navigate(Destination.EditProfile)
                },
                onLogout = {
                    navController.navigate(Destination.AuthGraph){
                        popUpTo(Destination.ProSales){
                            inclusive = false
                        }
                    }
                },
                onDetailReminderCustomer ={
                    navController.navigate(Destination.DetailReminderCustomer(it))
                }
            )
        }
        composable<Destination.AddLead>{
            val viewModel = koinViewModel<AddLeadViewModel>()
            AddLeadScreenRoot(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Destination.SelectSucursal>{

        }

        composable<Destination.DetailReminderCustomer>(
            enterTransition = {
                // Fade in with slide-in from bottom
                slideInVertically(
                    animationSpec = tween(600),
                    initialOffsetY = { fullHeight -> fullHeight }
                ) + fadeIn(
                    animationSpec = tween(600)
                )
            }
        ) { navBackEntry ->
            val detailReminder = navBackEntry.toRoute<Destination.DetailReminderCustomer>()

            DetailReminderCustomerScreenRoot(
                onBack = {
                    navController.navigateUp()
                }
            )

        }
        composable<Destination.EditProfile>{
            val viewModel = koinViewModel<UpdateProfileSMViewModel>()
            UpdateProfileSMSScreenRoot(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        
    }
}