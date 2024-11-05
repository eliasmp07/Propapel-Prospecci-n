package org.propapel.prospeccion.core.presentation.ui.permissions

import org.koin.core.module.Module
import org.koin.dsl.module


val permissionsModule: Module = module {
    single<PermissionsService> {
        PermissionsServiceImpl()
    }
}