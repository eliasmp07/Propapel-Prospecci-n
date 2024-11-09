package org.propapel.prospeccion.root.presentation.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.root.domain.models.Customer
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.img_no_data

@Composable
fun DonutChartInteractions(
    modifier: Modifier = Modifier,
    customers: List<Customer> = listOf()
) {
    val reminders = customers.flatMap { it.interactions }

    if (reminders.isEmpty()){
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .fillMaxWidth().padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Citas del mes anterior y actual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(Res.drawable.img_no_data),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "No haz tenidos citas este mes ni el anterior")
            }
        }
        return
    }else{
        // Obtener el mes actual y el mes anterior
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val previousMonthDate = currentDate.month.number - 1
        val previousYear = if (currentDate.month.number == 1) currentDate.year - 1 else currentDate.year


        // Contar recordatorios por mes
        val currentMonthCount = reminders.count {
            val reminderDate = Instant.fromEpochMilliseconds(it.interactionDate).toLocalDateTime(TimeZone.UTC).date
            reminderDate.year == currentDate.year && reminderDate.monthNumber == currentDate.monthNumber
        }

        val previousMonthCount = reminders.count {
            val reminderDate = Instant.fromEpochMilliseconds(it.interactionDate).toLocalDateTime(TimeZone.UTC).date
            reminderDate.year == previousYear && reminderDate.monthNumber == previousMonthDate
        }

        // Crear datos para el gráfico
        val testPieChartData: List<PieChartData> = listOf(
            PieChartData(
                partName = "${monthGet(currentDate.monthNumber)} $currentMonthCount" ,
                data = currentMonthCount.toDouble(),
                color = MaterialTheme.colorScheme.primary,
            ),
            PieChartData(
                partName = "${monthGet(previousMonthDate)} $previousMonthCount",
                data = previousMonthCount.toDouble(),
                color = Color(0xFF50A29F),
            )
        )

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .fillMaxWidth().padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Citas del mes anterior y actual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                DonutChart(
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    pieChartData = testPieChartData,
                    centerTitle = "Citas",
                    legendPosition = LegendPosition.BOTTOM,
                    centerTitleStyle = MaterialTheme.typography.bodyMedium,
                    textRatioStyle = MaterialTheme.typography.bodyMedium,
                    outerCircularColor = Color.LightGray,
                    innerCircularColor = Color.Gray,
                    ratioLineColor = Color.LightGray,
                    descriptionStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

fun monthGet(moth: Int): String {
    return when(moth){
        1 -> "Enero"
        2 -> "Febrero"
        3 -> "Marzo"
        4 -> "Abril"
        5 -> "Mayo"
        6 -> "Junio"
        7 -> "Julio"
        8 -> "Agosto"
        9 -> "Septiembre"
        10 -> "Octubre"
        11 -> "Noviembre"
        else -> "Diciembre"
    }
}