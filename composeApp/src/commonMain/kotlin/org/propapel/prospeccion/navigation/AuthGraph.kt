@file:OptIn(KoinExperimentalAPI::class)

package org.propapel.prospeccion.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.propapel.prospeccion.auth.presentation.intro.IntroScreenRoot
import org.propapel.prospeccion.auth.presentation.login.LoginScreenRoot
import org.propapel.prospeccion.auth.presentation.login.LoginViewModel
import org.propapel.prospeccion.navigation.utils.Destination

fun NavGraphBuilder.auth(
    onSuccess:(String) -> Unit,
    navController: NavHostController,
){
    navigation<Destination.AuthGraph>(
        startDestination = Destination.IntroScreen
    ){
        composable<Destination.IntroScreen> {
            IntroScreenRoot(
                onLogin = {
                    navController.navigate(Destination.LoginScreen)
                }
            )
        }
        composable<Destination.LoginScreen>{
            val viewModel = koinViewModel<LoginViewModel>()
            LoginScreenRoot(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Destination.ProSales) {
                        popUpTo(Destination.AuthGraph) {
                            inclusive = true
                        }
                    }
                    onSuccess("Inicion de session correcto")
                }
            )
        }
    }
}