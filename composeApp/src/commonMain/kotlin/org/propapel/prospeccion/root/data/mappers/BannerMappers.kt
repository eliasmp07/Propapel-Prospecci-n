package org.propapel.prospeccion.root.data.mappers

import org.propapel.prospeccion.root.data.dto.banners.BannerDto
import org.propapel.prospeccion.root.presentation.dashboard.components.mobile.Banner

fun BannerDto.toMapDomain(): Banner{
    return Banner(
        description = description,
        url = url?:"",
        id = id,
        type = type,
        imageUrl = imageUrl
    )
}