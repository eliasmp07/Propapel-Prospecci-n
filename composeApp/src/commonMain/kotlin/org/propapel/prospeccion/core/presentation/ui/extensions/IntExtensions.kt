package org.propapel.prospeccion.core.presentation.ui.extensions



fun Int?.orZero(): Int = this ?: 0

fun Int.addZeroAtStartInParenthesis() = if (this < 10) "($ZERO$this)" else "($this)"

inline fun Int.ifNotZero(action: (Int) -> Unit) {
    if (this != ZERO_INT) action(this)
}

fun Int.toPMOrAM(): String {
    return if (this >= 13) {
        "PM"
    } else {
        "AM"
    }
}

fun Int.toCorrectNumberMinute(): String {
    return when (this) {
        in 0..9 -> "0$this"
        else -> "$this"
    }
}

fun Int.toNumberAmAndPmHour(): String {
    return when (this) {
        in 1..9 -> "0$this"
        in 10..12 -> "$this"
        13 -> "01"
        14 -> "02"
        15 -> "03"
        16 -> "04"
        17 -> "05"
        18 -> "06"
        19 -> "07"
        20 -> "08"
        21 -> "09"
        22 -> "10"
        23 -> "11"
        else -> "12"
    }
}