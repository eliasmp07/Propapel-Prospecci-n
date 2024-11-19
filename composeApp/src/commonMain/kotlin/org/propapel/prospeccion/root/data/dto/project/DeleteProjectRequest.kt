package org.propapel.prospeccion.root.data.dto.project

import kotlinx.serialization.Serializable

@Serializable
data class DeleteProjectRequest(
    val motivos: String,
    val comments: String,
    val competencia: String
)
