package org.propapel.prospeccion.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import org.propapel.prospeccion.MainDesktopViewModel
import org.propapel.prospeccion.core.data.datastore.DATA_STORE_FILE_NAME
import org.propapel.prospeccion.core.data.datastore.createDataStore

actual fun platformModule(): Module {
    return module {
        single<DataStore<Preferences>> {
            createDataStore {
                DATA_STORE_FILE_NAME
            }
        }
        viewModelOf(::MainDesktopViewModel)
    }
}