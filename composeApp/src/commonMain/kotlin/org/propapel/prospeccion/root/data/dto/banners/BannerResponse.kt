package org.propapel.prospeccion.root.data.dto.banners

import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    val banners: List<BannerDto>
)
