@file:OptIn(KoinExperimentalAPI::class)

package org.propapel.prospeccion.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.propapel.prospeccion.navigation.utils.Destination
import org.propapel.prospeccion.root.presentation.homeRoot.HomeRootViewModel
import org.propapel.prospeccion.selectSucursal.presentation.root.GerentePanelScreen
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalScreenRoot
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalViewModel


fun NavGraphBuilder.gerente(
    navController: NavHostController,
) {
    navigation<Destination.Gerente>(
        startDestination = Destination.SelectSucursal
    ) {
        composable<Destination.SelectSucursal> {
            val viewModel = koinViewModel<SelectSucursalViewModel>()
            SelectSucursalScreenRoot(
                viewModel = viewModel,
                onSucursalMove = {
                    navController.navigate(Destination.GerenteDashboard(it)) {
                        popUpTo(Destination.SelectSucursal) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Destination.GerenteDashboard> { navBackStackEntry ->

            val viewModel = koinViewModel<HomeRootViewModel>()
            val sucursalId = navBackStackEntry.toRoute<Destination.GerenteDashboard>()

            LaunchedEffect(Unit){
                viewModel.insertSucursalId(sucursalId.sucursal)
            }

            GerentePanelScreen(
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
                        popUpTo(Destination.Gerente) {
                            inclusive = false
                        }
                    }
                },
                onSelectSucursal = {
                    navController.navigate(Destination.SelectSucursal) {
                        popUpTo(Destination.GerenteDashboard(sucursal = sucursalId.sucursal)) {
                            inclusive = true
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
    }
}