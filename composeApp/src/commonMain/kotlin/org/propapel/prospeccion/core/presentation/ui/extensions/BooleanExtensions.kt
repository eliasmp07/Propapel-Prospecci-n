package org.propapel.prospeccion.core.presentation.ui.extensions

fun Boolean?.isTrue(): Boolean = this == true

fun Boolean?.orTrue(): Boolean = this ?: true

fun Boolean?.orFalse(): Boolean = this ?: false

inline fun Boolean?.ifTrue(action: () -> Unit) {
    if (this == true) action()
}

inline fun Boolean?.ifFalse(action: () -> Unit) {
    if (this == false) action()
}
