package org.propapel.prospeccion

data class MainDesktopState(
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = false,
    val isManager: Boolean = false,
    val isRegional: Boolean = false
)
