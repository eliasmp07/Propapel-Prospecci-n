package org.propapel.prospeccion.auth.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.propapel.prospeccion.auth.data.AuthRepositoryImpl
import org.propapel.prospeccion.auth.domain.AuthRepository

val authDataModule  = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}