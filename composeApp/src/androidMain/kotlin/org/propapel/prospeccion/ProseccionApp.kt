package org.propapel.prospeccion

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.propapel.prospeccion.di.initKoin

class ProseccionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@ProseccionApp)
        }
    }
}