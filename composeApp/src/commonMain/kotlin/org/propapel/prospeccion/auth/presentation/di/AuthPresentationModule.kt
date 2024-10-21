package org.propapel.prospeccion.auth.presentation.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.propapel.prospeccion.auth.presentation.login.LoginViewModel

val authPresentationModule = module {
    viewModelOf(::LoginViewModel)
}