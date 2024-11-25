@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class,
    ExperimentalForeignApi::class
)

package org.propapel.prospeccion.di.alarm

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.root.domain.models.Reminder
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toNSDateComponents
import org.propapel.prospeccion.core.presentation.designsystem.components.converteDate
import org.propapel.prospeccion.core.presentation.ui.typeHour
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.UserNotifications.*

class AlarmHandlerIos : AlarmHandler {

    override fun setRecurringAlarm(reminder: Reminder) {
        // Convertir la fecha a localDateTime
        val date = convertLocalDate(reminder.reminderDate.toLong())

        // Crear el contenido de la notificación
        val notificationContent = UNMutableNotificationContent().apply {
            setTitle("Recordatorio")
            setBody("Tienes programado una cita con un cliente: ${reminder.customer.companyName} hoy a las ${date.hour} : ${date.minute} ${typeHour(date.hour)}")
            setSound(UNNotificationSound.defaultSound())

        }
        val trigger = createDateTrigger(reminder)
        // Crear un disparador para la notificación

        // Crear una solicitud de notificación
        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = reminder.reminderId.toString(),
            content = notificationContent,
            trigger = trigger
        )

        // Agregar la solicitud al centro de notificaciones
        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request) { error ->
            if (error != null) {
                println("Error al programar la notificación: ${error.localizedDescription}")
            } else {
                println("Notificación programada correctamente para ${convertLocalDate(reminder.reminderDate.toLong()).hour}")
            }
        }
    }

    override fun cancel(reminder: Reminder) {
        // Cancelar la notificación usando su identificador único
        val notificationId = "reminder_${reminder.reminderId}"
        val center = UNUserNotificationCenter.currentNotificationCenter()
        center.removePendingNotificationRequestsWithIdentifiers(listOf(notificationId))
        println("Notificación cancelada: $notificationId")
    }

    // Crear el trigger basado en la fecha y hora del recordatorio
    private fun createDateTrigger(reminder: Reminder): UNNotificationTrigger {
        val dateComponents = convertLocalDate(reminder.reminderDate.toLong()).toNSDateComponents()
        return UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(dateComponents, false)
    }

}
