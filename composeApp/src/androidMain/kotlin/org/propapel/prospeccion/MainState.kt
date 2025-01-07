package org.propapel.prospeccion

data class MainState(
    val apkFile: Long = 0L,
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = false,
    val isManager: Boolean = false
)