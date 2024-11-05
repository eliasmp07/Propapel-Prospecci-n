package org.propapel.prospeccion.core.domain.repository

import org.propapel.prospeccion.root.domain.models.Reminder

interface AlarmHandler {
    fun setRecurringAlarm(reminder: Reminder)
    fun cancel(reminder: Reminder)
}