package org.propapel.prospeccion.root.presentation.createProject

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

actual fun formatString(value: Double): String {
    val formatter = NSNumberFormatter()
    formatter.maximumFractionDigits = 2.toULong()
    formatter.minimumFractionDigits = 2.toULong()
    val number = NSNumber(value)  // Convertimos Double a NSNumber
    return formatter.stringFromNumber(number) ?: "0.00"
}