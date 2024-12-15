package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.core.data.networking.get
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.banners.BannerResponse
import org.propapel.prospeccion.root.data.mappers.toMapDomain
import org.propapel.prospeccion.root.domain.repository.BannerRepository
import org.propapel.prospeccion.root.presentation.dashboard.components.mobile.Banner

class BannerRepositoryImpl(
    private val httpClient: HttpClient
): BannerRepository {
    override suspend fun getAllBanners(): ResultExt<List<Banner>, DataError.Network> {
        val result = httpClient.get<BannerResponse>(
            route = "/banners/findAll"
        )

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(result.data.banners.map {
                    it.toMapDomain()
                })
            }
        }
    }
}
