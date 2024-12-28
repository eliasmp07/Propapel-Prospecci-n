package org.propapel.prospeccion.core.presentation.ui.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Long?.orZero(): Long = this ?: 0L

fun Long.toLocalDateTime(zone: TimeZone = TimeZone.UTC): LocalDateTime{
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(zone)
}