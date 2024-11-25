package org.propapel.prospeccion.root.presentation.createProject

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
actual fun formatString(value: Double): String {
    return String.format("%.2f", value)
}