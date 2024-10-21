package org.propapel.prospeccion.core.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class HttpBodyProSales<T>(
    val success: Boolean,
    val message: String,
    val error: String?,
    val data: T?
)