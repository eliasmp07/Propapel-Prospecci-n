package org.propapel.prospeccion.core.presentation.ui

fun typeHour(hora: Int): String{
    return if (hora >= 13) {
        "PM"
    } else {
        "AM"
    }
}