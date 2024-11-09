package org.propapel.prospeccion.root.presentation.updateProfile

import org.propapel.prospeccion.core.domain.AuthInfo

data class UpdateProfileSMState(
    val user: AuthInfo = AuthInfo(),
    val isLoading: Boolean = false,
    val image: String = "",
    val isError: Boolean = false,
    val error: String = "",
    val isSuccess: Boolean = false,
    val success: String = "",
)
