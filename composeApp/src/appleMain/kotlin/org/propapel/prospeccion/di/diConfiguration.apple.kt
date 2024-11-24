@file:OptIn(ExperimentalForeignApi::class)

package org.propapel.prospeccion.di

import org.koin.core.module.Module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.propapel.prospeccion.AppleMainViewModel
import org.propapel.prospeccion.core.data.datastore.DATA_STORE_FILE_NAME
import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.di.alarm.AlarmHandlerIos
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun platformModule(): Module {
    return module {
        viewModelOf(::AppleMainViewModel)
        single<DataStore<Preferences>> {
            createDataStoreIo()
        }
        single<AlarmHandler>{
            AlarmHandlerIos()
        }
    }
}

fun createDataStoreIo(): DataStore<Preferences> {
    return org.propapel.prospeccion.core.data.datastore.createDataStore {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
    }
}