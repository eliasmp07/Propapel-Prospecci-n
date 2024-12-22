package org.propapel.prospeccion.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import org.koin.core.component.KoinComponent
import org.propapel.prospeccion.MainActivity
import org.propapel.prospeccion.R
import org.propapel.prospeccion.alarm.ext.goAsync

class AlarmReceiver : BroadcastReceiver(), KoinComponent {

    companion object {
        const val MESSAGE = "message_reminder"
        const val REMINDER_ID = "reminder_id"
        const val NOTES = "description_reminder"
        const val CHANNEL_ID = "reminder_channel"
        const val detailNotificacion = "detail"

    }

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) = goAsync {
        if (context == null || intent == null) return@goAsync
        val message = intent.getStringExtra(MESSAGE) ?: return@goAsync
        val id = intent.getStringExtra(REMINDER_ID) ?: return@goAsync
        val notes = intent.getStringExtra(NOTES)?:"No tienes notas para la cita"
        createNotificationChannel(context)
        showNotification(
            reminderId = id.toInt(),
            message = message,
            notes = notes ,
            context = context
        )
    }

    private fun showNotification(
        message: String,
        reminderId: Int,
        notes: String,
        context: Context
    ) {
        val intent = Intent(
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(
                detailNotificacion,
                "Detalle notificacion"
            )
        }

        // Create a PendingIntent that starts the activity when the button is clicked
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setContentTitle("Recordatorio")
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentText(message)
            .setSmallIcon(R.drawable.appointmente_today)
            .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI) // Configura el sonido
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            reminderId.hashCode(),
            notification
        )
    }


    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Reminder Appointment",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Get your appointment reminder!"
            channel.setSound(
                android.provider.Settings.System.DEFAULT_NOTIFICATION_URI,
                null
            ) // Establecer sonido predeterminado
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
