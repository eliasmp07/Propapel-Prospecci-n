package org.propapel.prospeccion.root.presentation.dates
sealed interface DatesAction {
    data object OnRefresh: DatesAction
    data class OnDetailReminder(val reminderId: String): DatesAction
}