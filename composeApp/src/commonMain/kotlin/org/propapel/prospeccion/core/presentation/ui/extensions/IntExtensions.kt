package org.propapel.prospeccion.core.presentation.ui.extensions



fun Int?.orZero(): Int = this ?: 0

fun Int.addZeroAtStartInParenthesis() = if (this < 10) "($ZERO$this)" else "($this)"

inline fun Int.ifNotZero(action: (Int) -> Unit) {
    if (this != ZERO_INT) action(this)
}