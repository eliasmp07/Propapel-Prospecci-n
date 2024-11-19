package org.propapel.prospeccion.core.presentation.ui.extensions


inline fun <T> tryOrDefault(defaultValue: T, blockToTry: () -> T): T = try {
    blockToTry()
} catch (t: Exception) {
    defaultValue
} catch (t: RuntimeException) {
    defaultValue
} catch (t: Throwable) {
    defaultValue
} catch (t: IllegalArgumentException) {
    defaultValue
} catch (t: NumberFormatException) {
    defaultValue
}

suspend inline fun <T> tryOrDefaultSuspend(defaultValue: T, blockToTry: suspend () -> T): T = try {
    blockToTry()
} catch (t: Exception) {
    defaultValue
} catch (t: RuntimeException) {
    defaultValue
} catch (t: Throwable) {
    defaultValue
}

inline fun <T, U, R> Pair<T?, U?>.biLet(body: (T, U) -> R): R? {
    val first = first
    val second = second
    if (first != null && second != null) {
        return body(first, second)
    }
    return null
}

inline fun <T, U, V, R> Triple<T?, U?, V?>.biLet(body: (T, U, V) -> R): R? {
    val first = first
    val second = second
    val third = third
    if (first != null && second != null && third != null) {
        return body(first, second, third)
    }
    return null
}


inline fun Any?.ifNull(action: () -> Unit) {
    if (this == null) action()
}

inline fun Any?.isNotNull() = this != null

inline fun Any?.isNull() = this == null