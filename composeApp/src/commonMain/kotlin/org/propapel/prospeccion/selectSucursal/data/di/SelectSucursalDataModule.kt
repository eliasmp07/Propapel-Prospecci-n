package org.propapel.prospeccion.selectSucursal.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.propapel.prospeccion.selectSucursal.data.repository.SucursalesRepositoryImpl
import org.propapel.prospeccion.selectSucursal.data.repository.UserRepositoryImpl
import org.propapel.prospeccion.selectSucursal.domain.repository.SucursalesRepository
import org.propapel.prospeccion.selectSucursal.domain.repository.UserRepository

val selectSucursalDataModule = module {
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::SucursalesRepositoryImpl).bind<SucursalesRepository>()
}