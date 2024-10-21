package org.propapel.prospeccion.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.data.networking.HttpClientFactory
import org.propapel.prospeccion.core.data.repository.SessionStorageImpl

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
    single<SessionStorage> {
        SessionStorageImpl(get<DataStore<Preferences>>())
    }
}