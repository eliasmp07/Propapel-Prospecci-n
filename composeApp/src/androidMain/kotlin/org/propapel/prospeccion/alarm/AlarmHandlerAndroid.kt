package org.propapel.prospeccion.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.core.presentation.ui.typeHour
import org.propapel.prospeccion.root.domain.models.Reminder
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(time: Long): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time

    val timeZone = java.util.TimeZone.getDefault()
    val offset = timeZone.getOffset(calendar.timeInMillis)

    // Ajustar la hora local restando el offset de la zona horaria
    val localTimeInMillis = calendar.timeInMillis - offset
    return localTimeInMillis
}


data class MessageSchedule(
    val moment: Long,
    val message: String
)

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
        val convertedTime = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong()).toLocalDateTime(TimeZone.currentSystemDefault())


        val moments = listOf(
            MessageSchedule(
                moment = baseMoment,
                message = "Hoy es tu cita con ${reminder.customer.companyName} a las ${convertedTime.hour}:${convertedTime.minute} ${typeHour(convertedTime.hour)}"
            ),
            MessageSchedule(
                moment = formatTime(reminder.reminderDate.toLong() - (24 * 60 * 60 * 1000)),
                message = "Recuerda que mañana tienes una cita con ${reminder.customer.companyName} a las ${convertedTime.hour}:${convertedTime.minute} ${typeHour(convertedTime.hour)}."
            ),
            MessageSchedule(
                moment = formatTime(reminder.reminderDate.toLong() - (60 * 60 * 1000)),
                message = "En una hora tienes tu cita con ${reminder.customer.companyName}."
            )
        )

        moments.forEachIndexed { index, moment ->
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                moment.moment,
                createPendingIntent(
                    reminder =  reminder,
                    index = index,
                    notes = reminder.description,
                    message =  moment.message
                )
            )
        }
    }


    override fun cancel(reminder: Reminder) {
        val pending = createPendingIntent(reminder)
        alarmManager.cancel(pending)
    }

    private fun createPendingIntent(
        reminder: Reminder,
        index: Int = 1,
        notes: String = "",
        message: String = ""
    ): PendingIntent {
        val intent = Intent(
            context,
            AlarmReceiver::class.java
        ).apply {
            putExtra(
                AlarmReceiver.MESSAGE,
                message
            )
            putExtra(
                AlarmReceiver.REMINDER_ID,
                reminder.reminderId.toString()
            )
            putExtra(
                AlarmReceiver.NOTES,
                notes

            )
        }
        return PendingIntent.getBroadcast(
            context,
            reminder.reminderId.hashCode() + index,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
