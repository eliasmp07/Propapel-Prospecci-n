package org.propapel.prospeccion.selectSucursal.presentation.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalViewModel

val selectSucursalModule = module {
    viewModelOf(::SelectSucursalViewModel)
}