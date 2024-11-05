package org.propapel.prospeccion.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import org.propapel.prospeccion.MainViewModel
import org.propapel.prospeccion.alarm.AlarmHandlerAndroid
import org.propapel.prospeccion.core.data.datastore.DATA_STORE_FILE_NAME
import org.propapel.prospeccion.core.domain.repository.AlarmHandler

actual fun platformModule(): Module {
    return module {
        single<DataStore<Preferences>> {
            createDataStore(androidContext())
        }
        single<AlarmHandler>{
            AlarmHandlerAndroid(androidContext())
        }
        viewModelOf(::MainViewModel)
    }
}

fun createDataStore(context: Context): DataStore<Preferences> {
    return org.propapel.prospeccion.core.data.datastore.createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}
