package org.propapel.prospeccion.core.data.mappers

import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.data.networking.AuthInfoSerializable

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
         name = name,
        isAdmin = isAdmin,
        lastname = lastname,
        email = email,
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
        isAdmin = isAdmin,
        lastname = lastname,
        email = email,
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
        image = image?:"",
        userId = userId.toString()
    )
}