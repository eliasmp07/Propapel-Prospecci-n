package org.propapel.prospeccion.root.domain.repository

import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.domain.models.Reminder

interface ReminderRepository {
    suspend fun getAllReminder(): ResultExt<List<Reminder>, DataError.Network>
    suspend fun getAllMyReminders(): ResultExt<List<Reminder>, DataError.Network>
}