package org.propapel.prospeccion.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.koin.android.ext.koin.androidContext
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import org.propapel.prospeccion.MainViewModel
import org.propapel.prospeccion.alarm.AlarmHandlerAndroid
import org.propapel.prospeccion.core.data.datastore.DATA_STORE_FILE_NAME
import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.data.UpdateAppRepositoryImpl
import org.propapel.prospeccion.updateApk.UpdateAppRepository

actual fun platformModule(): Module {
    return module {
        single {
            FirebaseRemoteConfig.getInstance().apply {
                setConfigSettingsAsync(remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 30
                })
                fetchAndActivate()
            }
        }

        single<UpdateAppRepository> { // Bind the interface to the implementation
            UpdateAppRepositoryImpl(
                remoteConfig = get(),
                context = androidContext()
            )
        }
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
