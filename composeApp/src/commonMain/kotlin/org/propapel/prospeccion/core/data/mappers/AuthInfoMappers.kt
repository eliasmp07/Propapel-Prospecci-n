package org.propapel.prospeccion.core.data.mappers

import org.propapel.prospeccion.core.data.networking.AuthInfoSerializable
import org.propapel.prospeccion.core.domain.AuthInfo

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
         name = name,
        lastname = lastname,
        email = email,
        sucursales = sucursales,
        puesto = puesto,
        roles = roles,
        userId = userId.toInt(),
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
        image = image
    )
}

fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        name = name,
        lastname = lastname,
        sucursales = sucursales,
        puesto = puesto,
        roles =  roles,
        email = email,
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
        image = image?:"",
        userId = userId.toString()
    )
}