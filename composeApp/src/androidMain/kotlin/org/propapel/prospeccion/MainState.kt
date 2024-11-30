package org.propapel.prospeccion

data class MainState(
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = false,
    val isManager: Boolean = false
)