package org.propapel.prospeccion.root.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val name: String,
    val lastname: String,
    val phone: String,
    val image: String
)
