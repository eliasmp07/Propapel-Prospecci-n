package org.propapel.prospeccion.auth.presentation.login

import org.propapel.prospeccion.core.presentation.ui.UiText

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLogging: Boolean = false,
    val isError: Boolean = false,
    val error: UiText = UiText.DynamicString("Error"),
    val loginSuccess: Boolean = false
)
