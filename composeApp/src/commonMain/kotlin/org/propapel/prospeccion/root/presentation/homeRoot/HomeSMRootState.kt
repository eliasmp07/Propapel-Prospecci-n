package org.propapel.prospeccion.root.presentation.homeRoot

import androidx.compose.runtime.Stable
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.root.domain.models.Reminder

@Stable
data class HomeSMRootState(
    val reminderDay: List<Reminder> = listOf(),
    val user: AuthInfo = AuthInfo(),
    val reminders: List<Reminder> = listOf(),
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date, )
