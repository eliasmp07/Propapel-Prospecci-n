package org.propapel.prospeccion.di

import org.koin.core.context.startKoin

import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.propapel.prospeccion.auth.data.di.authDataModule
import org.propapel.prospeccion.auth.presentation.di.authPresentationModule
import org.propapel.prospeccion.core.data.di.coreDataModule
import org.propapel.prospeccion.root.data.di.rootDataModule
import org.propapel.prospeccion.root.presentation.di.proSalesPresentationModule

expect fun platformModule():Module

fun initKoin(config:KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(
            platformModule(),
            //Core
            coreDataModule,
            //Auth
            authPresentationModule,
            authDataModule,
            //ProSales
            proSalesPresentationModule,
            rootDataModule
        )
    }
}
