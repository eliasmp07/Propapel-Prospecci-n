package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

@Composable
fun SalesPerformanceTable() {
    // Obtener la fecha actual
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    // Extraer el mes actual
    val currentMonth = currentDateTime.month

    // Calcular el mes anterior (considerando cambio de año)
    val previousMonth = currentDateTime.date.minus(DatePeriod(months = 1)).month

    println("Mes actual: $currentMonth")
    println("Mes anterior: $previousMonth")
}