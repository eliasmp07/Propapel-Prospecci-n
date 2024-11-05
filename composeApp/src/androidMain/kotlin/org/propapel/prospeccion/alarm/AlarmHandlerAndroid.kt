package org.propapel.prospeccion.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.root.domain.models.Reminder
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class AlarmHandlerAndroid(
    private val context: Context
) : AlarmHandler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setRecurringAlarm(reminder: Reminder) {

        val date = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong())

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            date.toEpochMilliseconds(),
            createPendingIntent(reminder)
        )
    }


    override fun cancel(reminder: Reminder) {
        val pending = createPendingIntent(reminder)
        alarmManager.cancel(pending)
    }

    private fun createPendingIntent(reminder: Reminder): PendingIntent {
        val converteTime = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong()).toLocalDateTime(TimeZone.UTC)
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.REMINDER_ID, reminder.reminderId.toString())
            putExtra(AlarmReceiver.COSTUMER, reminder.customer.contactName)
            putExtra(AlarmReceiver.MINUTS, converteTime.minute.toString())
            putExtra(AlarmReceiver.HOUR, converteTime.hour.toString())
        }
        return PendingIntent.getBroadcast(
            context,
            reminder.reminderId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
