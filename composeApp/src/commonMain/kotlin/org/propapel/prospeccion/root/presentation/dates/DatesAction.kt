package org.propapel.prospeccion.root.presentation.dates
sealed interface DatesAction {
    data object OnRefresh: DatesAction
}