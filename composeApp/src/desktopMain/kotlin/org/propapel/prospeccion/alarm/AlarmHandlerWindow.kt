package org.propapel.prospeccion.alarm

import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.root.domain.models.Reminder
import java.awt.AWTException
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.util.Timer
import java.util.TimerTask

class AlarmHandlerWindow(): AlarmHandler {


    private val timers = mutableMapOf<String, Timer>()

    override fun setRecurringAlarm(reminder: Reminder) {
        if (!SystemTray.isSupported()) {
            println("System tray not supported!")
            return
        }

        val tray = SystemTray.getSystemTray()
        val image = Toolkit.getDefaultToolkit().createImage("path/to/icon.png")
        val trayIcon = TrayIcon(image, "Propapel Reminder")
        trayIcon.isImageAutoSize = true
        trayIcon.toolTip = "Propapel Reminder Notifications"

        try {
            tray.add(trayIcon)
        } catch (e: AWTException) {
            println("TrayIcon could not be added.")
            return
        }

        val timer = Timer()
        timers[reminder.reminderId.toString()] = timer
        timer.schedule(object : TimerTask() {
            override fun run() {
                trayIcon.displayMessage(
                    "Reminder: ${reminder.description}",
                    "Don't forget: ${reminder.customer.contactName}",
                    TrayIcon.MessageType.INFO
                )
            }
        }, 0, reminder.reminderDate.toLong()) // `intervalMillis` debería estar en el modelo `Reminder`.
    }

    override fun cancel(reminder: Reminder) {
        timers[reminder.reminderId.toString()]?.cancel()
        timers.remove(reminder.reminderId.toString())
    }
}