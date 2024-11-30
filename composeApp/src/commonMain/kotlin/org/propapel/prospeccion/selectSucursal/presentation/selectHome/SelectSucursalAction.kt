package org.propapel.prospeccion.selectSucursal.presentation.selectHome

sealed interface SelectSucursalAction {
    data object OnLeftSelectedClick: SelectSucursalAction
    data object OnRightSelectedClick: SelectSucursalAction
    data class OnSucusalChange(val sucursalId: Int): SelectSucursalAction
    data class OnSucursalSelectedClick(val sucursalId: Int): SelectSucursalAction
}