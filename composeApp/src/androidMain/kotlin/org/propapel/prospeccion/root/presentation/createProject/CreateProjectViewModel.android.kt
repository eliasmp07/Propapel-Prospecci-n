package org.propapel.prospeccion.root.presentation.createProject

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
actual fun formatString(value: Double): String {
    return String.format("%.2f", value)
}

actual fun formatString(value: kotlin.Double): kotlin.String {
    TODO("Not yet implemented")
}