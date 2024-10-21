package org.propapel.prospeccion.root.presentation.account

sealed interface AccountSMAction {
    data object OnSelectSucursal: AccountSMAction
    data object EditProfileClick: AccountSMAction
    data object OnLogoutClick: AccountSMAction
}