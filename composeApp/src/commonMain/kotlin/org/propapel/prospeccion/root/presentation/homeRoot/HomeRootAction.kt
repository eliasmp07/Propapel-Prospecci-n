package org.propapel.prospeccion.root.presentation.homeRoot

sealed interface HomeRootAction {
    data object OnLogoutClick: HomeRootAction
}