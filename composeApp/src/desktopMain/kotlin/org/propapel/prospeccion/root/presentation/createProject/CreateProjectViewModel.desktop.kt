package org.propapel.prospeccion.root.presentation.createProject

@Suppress("DefaultLocale")
actual fun formatString(value: Double): String {
    return String.format("%.2f", value)
}

actual fun formatString(value: kotlin.Double): kotlin.String {
    TODO("Not yet implemented")
}