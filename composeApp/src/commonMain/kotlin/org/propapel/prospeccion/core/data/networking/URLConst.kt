package org.propapel.prospeccion.core.data.networking

object URLBackend {
    const val BASE_URL_DEV = "http://172.17.1.79:3002"
    const val BASE_URL = "http://3.144.8.170:3002"

    fun detectMode(isProduction: Boolean = false): String{
        return if (isProduction) BASE_URL else BASE_URL_DEV
    }
}