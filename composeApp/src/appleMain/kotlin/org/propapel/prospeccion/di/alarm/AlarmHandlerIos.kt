package org.propapel.prospeccion.di.alarm

import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.root.domain.models.Reminder

class AlarmHandlerIos(): AlarmHandler {
    override fun setRecurringAlarm(reminder: Reminder) {
        TODO("Not yet implemented")
    }

    override fun cancel(reminder: Reminder) {
        TODO("Not yet implemented")
    }
}