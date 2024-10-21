package org.propapel.prospeccion.root.presentation.leads.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Interaction
import org.propapel.prospeccion.root.domain.models.Reminder

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.LocalDateTime

// Convertir la fecha de interacción (milisegundos) a una representación YearMonth
fun Interaction.toYearMonth(): String {
    val instant = Instant.fromEpochMilliseconds(this.interactionDate)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}" // Formato: Año-Mes
}

// Agrupar las interacciones por mes y contar cuántas visitas hay en cada mes
fun aggregateInteractionsByMonth(interactions: List<Interaction>): Map<String, Int> {
    return interactions.groupingBy { it.toYearMonth() }.eachCount()
}

@Composable
fun MonthlyVisitsChart(
    title: String,
    interactions: List<Interaction>,
    modifier: Modifier = Modifier
) {
    // Agrupar interacciones por mes
    val interactionDataByMonth = aggregateInteractionsByMonth(interactions)

    // Generar datos para el eje X (meses) y el eje Y (conteo de interacciones)
    val xAxisData = interactionDataByMonth.keys.sorted() // Ordenar los meses cronológicamente
    val yAxisData = interactionDataByMonth.values.map { it.toDouble() }

    val lineParameters = LineParameters(
        label = "Visitas por mes",
        data = yAxisData,
        lineColor = Color(0xFF22A699),
        lineType = LineType.CURVED_LINE,
        lineShadow = true,
    )

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Ver todo", fontSize = 13.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                linesParameters = listOf(lineParameters),
                isGrid = true,
                gridColor = Color.Blue,
                xAxisData = xAxisData, // Meses en el eje X
                animateChart = true,
                showGridWithSpacer = true,
                yAxisStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                ),
                xAxisStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.W400
                ),
                yAxisRange = 14,
                oneLineChart = false,
                gridOrientation = GridOrientation.VERTICAL
            )
        }
    }
}

