package org.propapel.prospeccion.root.data.dto.banners

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerDto(
    val id: Int,
    val imageUrl: String,
    val url: String?,
    val type: String,
    val description: String,
    @SerialName("created_at") val createAt: String,
    @SerialName("updated_at") val updateAt: String,
)
