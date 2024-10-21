package org.propapel.prospeccion.auth.presentation.login

sealed interface LoginAction {

    data object OnLoginClick : LoginAction
    data class OnEmailChange(val email: String): LoginAction
    data class OnPasswordChange(val password: String): LoginAction
    data object OnDismissDialogErrorClick : LoginAction
    data object HideError: LoginAction
}