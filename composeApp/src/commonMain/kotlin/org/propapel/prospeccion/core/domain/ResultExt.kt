package org.propapel.prospeccion.core.domain

import org.propapel.prospeccion.core.domain.utils.Error

sealed interface ResultExt<out D, out E: Error> {
    data class Success<out D>(val data: D): ResultExt<D, Nothing>
    data class Error<out E: org.propapel.prospeccion.core.domain.utils.Error>(val error: E):
        ResultExt<Nothing, E>
}

inline fun <T, E: Error, R> ResultExt<T, E>.map(map: (T) -> R): ResultExt<R, E> {
    return when(this) {
        is ResultExt.Error -> ResultExt.Error(error)
        is ResultExt.Success -> ResultExt.Success(map(data))
    }
}

fun <T, E: Error> ResultExt<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { Unit }
}

typealias EmptyResult<E> = ResultExt<Unit, E>