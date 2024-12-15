package org.propapel.prospeccion.root.domain.repository

import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.presentation.dashboard.components.mobile.Banner

interface BannerRepository {
    suspend fun getAllBanners(): ResultExt<List<Banner>, DataError.Network>
}