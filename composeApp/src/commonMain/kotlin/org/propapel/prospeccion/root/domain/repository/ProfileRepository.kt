package org.propapel.prospeccion.root.domain.repository

import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.UserDto

interface ProfileRepository {
    suspend fun updateProfile(name: String, lastname: String, image: String, phone: String):ResultExt<AuthInfo, DataError.Network>
    suspend fun getAllUsers(): ResultExt<List<User>, DataError.Network>
}

data class User(
    val lastname: String = "",
    val name: String = "",
    val phone: String = "",
    val image: String = "",
    val isAdmin: Boolean = false
)

fun UserDto.toUser(): User{
    return User(
        lastname,
        name,
        phone?:"",
        image?:"",
        isAdmin
    )
}