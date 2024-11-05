package org.propapel.prospeccion.root.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.model.PieChartData
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.domain.models.Customer

@Composable
fun DonutChartServices(
    modifier: Modifier = Modifier,
    customers: List<Customer> = listOf()
) {
    val reminders = customers.flatMap { it.reminders }

    // Obtener el mes actual y el mes anterior
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    val previousMonthDate = currentDate.month.number - 1

    // Contar recordatorios por mes
    val currentMonthCount = reminders.count {
        val reminderDate = Instant.fromEpochMilliseconds(it.reminderDate.toLong()).toLocalDateTime(TimeZone.UTC).date
        reminderDate.year == currentDate.year && reminderDate.monthNumber == currentDate.monthNumber
    }

    val previousMonthCount = reminders.count {
        val reminderDate = Instant.fromEpochMilliseconds(it.reminderDate.toLong()).toLocalDateTime(TimeZone.UTC).date
        reminderDate.year == previousMonthDate && reminderDate.monthNumber == previousMonthDate
    }

    // Crear datos para el gráfico
    val testPieChartData: List<PieChartData> = listOf(
        PieChartData(
            partName = currentDate.month.toString() ,
            data = currentMonthCount.toDouble(),
            color = Color(0xFF0B666A),
        ),
        PieChartData(
            partName = currentDate.month.toString(),
            data = previousMonthCount.toDouble(),
            color = Color(0xFF50A29F),
        )
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
            Text(text = "Citas del mes anterior y actual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            DonutChart(
                modifier = Modifier.fillMaxWidth().height(400.dp),
                pieChartData = testPieChartData,
                centerTitle = "Citas",
                legendPosition = LegendPosition.BOTTOM,
                centerTitleStyle = TextStyle(color = Color(0xFF071952)),
                outerCircularColor = Color.LightGray,
                innerCircularColor = Color.Gray,
                ratioLineColor = Color.LightGray,
                descriptionStyle = TextStyle(fontSize = 10.sp, textAlign = TextAlign.Center)
            )
        }
    }
}
