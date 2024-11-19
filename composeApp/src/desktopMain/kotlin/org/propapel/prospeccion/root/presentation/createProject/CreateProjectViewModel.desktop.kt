package org.propapel.prospeccion.root.presentation.createProject

@Suppress("DefaultLocale")
actual fun formatString(value: Double): String {
    return String.format("%.2f", value)
}