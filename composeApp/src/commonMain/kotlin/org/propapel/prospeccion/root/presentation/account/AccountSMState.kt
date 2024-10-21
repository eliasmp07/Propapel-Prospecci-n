package org.propapel.prospeccion.root.presentation.account

import org.propapel.prospeccion.core.domain.AuthInfo

data class AccountSMState(
    val user: AuthInfo = AuthInfo()
)
