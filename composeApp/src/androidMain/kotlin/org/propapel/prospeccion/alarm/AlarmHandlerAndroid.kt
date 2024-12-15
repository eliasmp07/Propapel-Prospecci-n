package org.propapel.prospeccion.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.root.domain.models.Reminder
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(time: Long): Long{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time

    val timeZone = java.util.TimeZone.getDefault()
    val offset = timeZone.getOffset(calendar.timeInMillis)

    // Ajustar la hora local restando el offset de la zona horaria
    val localTimeInMillis = calendar.timeInMillis - offset
    return localTimeInMillis
}

/**
 *  Manejador de las notificaciones
 *
 *  @param context Contexto para poder inicializar el alarm manager
 */
class AlarmHandlerAndroid(
    private val context: Context
) : AlarmHandler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setRecurringAlarm(reminder: Reminder) {
        val baseMoment = formatTime(reminder.reminderDate.toLong())

        val moments = listOf(
            baseMoment, // Momento exacto
            formatTime(reminder.reminderDate.toLong() - (24 * 60 * 60 * 1000)),
            formatTime(reminder.reminderDate.toLong() - (60 * 60 * 1000))
        )

        moments.forEachIndexed { index, moment ->
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                moment,
                createPendingIntent(reminder, index )
            )
        }
    }




    override fun cancel(reminder: Reminder) {
        val pending = createPendingIntent(reminder)
        alarmManager.cancel(pending)
    }

    private fun createPendingIntent(reminder: Reminder, index: Int = 1): PendingIntent {
        val converteTime = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong()).toLocalDateTime(TimeZone.UTC)
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.REMINDER_ID, reminder.reminderId.toString())
            putExtra(AlarmReceiver.COSTUMER, reminder.customer.contactName)
            putExtra(AlarmReceiver.MINUTS, converteTime.minute.toString())
            putExtra(AlarmReceiver.HOUR, converteTime.hour.toString())
        }
        return PendingIntent.getBroadcast(
            context,
            reminder.reminderId.hashCode() + index,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
