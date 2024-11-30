package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.ui.extensions.previousMoth
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.presentation.dashboard.components.monthGet
import org.propapel.prospeccion.selectSucursal.domain.model.CustomerUser

@Composable
fun LeadAddThisMonth(
    modifier: Modifier = Modifier,
    customers: List<CustomerUser>
){

    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val previousMonthDate = currentDate.previousMoth()


    val (nuevosLast, desarrolloLast, recuperacionLast) =
        calculateCountsLeads(
            customers,
            currentDate.year,
            previousMonthDate
        )
    val (nuevosNow, desarrolloNow, recuperacionNow) =
        calculateCountsLeads(
            customers,
            currentDate.year,
            currentDate.monthNumber
        )


    val barColors = listOf(
        Color(0xFF6C3428), // Cerrados
        Color(0xFFBA704F), // Perdidos
        Color(0xFFDFA878)  // En negociación
    )

    val testBarParameters: List<BarParameters> = listOf(
        BarParameters(
            "Nuevos",
            listOf(
                nuevosLast.toDouble(),
                nuevosNow.toDouble()
            ),
            barColors[0]
        ),
        BarParameters(
            "Desarrollo",
            listOf(
                desarrolloLast.toDouble(),
                desarrolloNow.toDouble()
            ),
            barColors[1]
        ),
        BarParameters(
            "En recuperacion",
            listOf(
                recuperacionLast.toDouble(),
                recuperacionNow.toDouble()
            ),
            barColors[2]
        )
    )

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF60DDAD),
        targetValue = Color(0xFF4285F4),
        animationSpec = infiniteRepeatable(
            tween(1000),
            RepeatMode.Reverse
        ),
        label = "color"
    )
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Clientes ingresados",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            BarChart(
                chartParameters = testBarParameters,
                gridColor = Color.DarkGray,
                xAxisData = listOf(
                    monthGet(previousMonthDate),
                    monthGet(currentDate.monthNumber)
                ),
                isShowGrid = true,
                animateChart = true,
                showGridWithSpacer = true,
                yAxisStyle = TextStyle(
                    fontSize = 12.sp,
                    color = Color.DarkGray
                ),
                xAxisStyle = TextStyle(
                    fontSize = 12.sp,
                    color = Color.DarkGray
                ),
                yAxisRange = 14,
                barWidth = 20.dp
            )
        }
    }
}

fun calculateCountsLeads(
    customers: List<CustomerUser>,
    year: Int,
    month: Int
): Triple<Int, Int, Int> {
    var nuevos = 0
    var desarrollo = 0
    var recuperacion = 0

    customers.forEach { customer ->
        if (customer.createdAt.year == year && customer.createdAt.monthNumber == month){
            when{
                customer.typeOfClient.contains(TypeOfClient.NUEVO.name) -> nuevos++
                customer.typeOfClient.contains(TypeOfClient.DESARROLLO.name) -> desarrollo++
                else -> recuperacion++
            }
        }
    }
    return Triple(
        nuevos,
        desarrollo,
        recuperacion
    )
}