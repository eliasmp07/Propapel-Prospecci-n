package org.propapel.prospeccion.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import org.koin.core.component.KoinComponent
import org.propapel.prospeccion.MainActivity
import org.propapel.prospeccion.R
import org.propapel.prospeccion.alarm.ext.goAsync

class AlarmReceiver : BroadcastReceiver(), KoinComponent {

    companion object {
        const val REMINDER_ID = "habit_id"
        const val CHANNEL_ID = "habits_channel"
        const val COSTUMER = "cliente"
        const val MINUTS = "minuts"
        const val HOUR = "hour"
        const val detailNotificacion = "detail"

    }

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (context == null || intent == null) return@goAsync
        Log.d("AlarmReceiver", "onReceive called")
        val id = intent.getStringExtra(REMINDER_ID) ?: return@goAsync
        val customer = intent.getStringExtra(COSTUMER) ?: return@goAsync
        val minuts = intent.getStringExtra(MINUTS)?: return@goAsync
        val hour = intent.getStringExtra(HOUR)?:return@goAsync
        createNotificationChannel(context)
        showNotification(id.toInt(), customer = customer, hour = hour, minuts = minuts, context)
    }

    private fun showNotification(reminderId: Int, customer: String, hour: String, minuts: String, context: Context) {
        // Create an Intent to open your app's main activity
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(detailNotificacion, "Detalle notificacion")
        }

        // Create a PendingIntent that starts the activity when the button is clicked
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Recordatorio")
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentText("Hoy es tu cita con ${customer} a las $hour: $minuts ${org.propapel.prospeccion.core.presentation.ui.typeHour(hour.toInt())} 🕐🤝")
            .setSmallIcon(R.drawable.appointmente_today)
            .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI) // Configura el sonido
            .setAutoCancel(true)
            .build()

        notificationManager.notify(reminderId.hashCode(), notification)
    }


    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Reminder Appointment",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Get your appointment reminder!"
            channel.setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI, null) // Establecer sonido predeterminado
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
