package org.propapel.prospeccion.root.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateProjectRequest(
    val nameProject: String,
    val valorProject: String,
    val progress: String,
    val costumerId: String,
)