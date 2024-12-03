package org.propapel.prospeccion.root.presentation.leads.components.mobile

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.presentation.ui.extensions.previousMoth
import org.propapel.prospeccion.core.presentation.ui.extensions.previousYear
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate
import org.propapel.prospeccion.root.presentation.dashboard.components.monthGet
import org.propapel.prospeccion.selectSucursal.domain.model.ProjectUser
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.components.ContentValidateLastMoth
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.components.calculoPorcentual
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.components.infoValidateProjects
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.ic_product_otline
import prospeccion.composeapp.generated.resources.projects_ic_dra


@Composable
fun BarCharProjects(
    modifier: Modifier = Modifier,
    projects: List<Project> = listOf()
) {
    // Helper para obtener los conteos

    // Fechas actuales y del mes anterior
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val previousMonthDate = currentDate.previousMoth()


    val (isLow, diffence) = infoValidateProject(
        projects = projects,
        currentMoth = currentDate.monthNumber,
        mothLast = previousMonthDate
    )


    // Calcular los conteos
    val (cierreNow, negociacionNow, perdidoNow) =
        calculateCounts(
            projects,
            currentDate.year,
            currentDate.monthNumber
        )
    val (cierreLast, negociacionLast, perdidoLast) =
        calculateCounts(
            projects,
            currentDate.year,
            previousMonthDate
        )

    // Configuración del gráfico
    val barColors = listOf(
        Color(0xFF6C3428), // Cerrados
        Color(0xFFBA704F), // Perdidos
        Color(0xFFDFA878)  // En negociación
    )

    val testBarParameters: List<BarParameters> = listOf(
        BarParameters(
            "Cerrados",
            listOf(
                cierreLast.toDouble(),
                cierreNow.toDouble()
            ),
            barColors[0]
        ),
        BarParameters(
            "Perdidos",
            listOf(
                perdidoLast.toDouble(),
                perdidoNow.toDouble()
            ),
            barColors[1]
        ),
        BarParameters(
            "En negociacion",
            listOf(
                negociacionLast.toDouble(),
                negociacionNow.toDouble()
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = vectorResource(Res.drawable.projects_ic_dra),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Proyectos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                ContentValidateLastMoth(
                    isLow = !isLow,
                    diffence
                )
            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "${monthGet(currentDate.monthNumber)} vs ${monthGet(previousMonthDate)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = animatedColor
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
                    fontSize = 14.sp,
                    color = Color.DarkGray
                ),
                xAxisStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.DarkGray
                ),
                yAxisRange = 15,
                barWidth = 20.dp
            )
        }
    }
}

fun calculateCounts(
    projects: List<Project>,
    year: Int,
    month: Int
): Triple<Int, Int, Int> {
    var cierre = 0
    var negociacion = 0
    var perdido = 0

    projects.forEach { project ->
        if (project.created.year == year && project.created.monthNumber == month) {
            when {
                project.status.contains(
                    "Cierre",
                    ignoreCase = true
                ) -> cierre++
                project.status == "En negociacion" -> negociacion++
                else -> perdido++
            }
        }
    }
    return Triple(
        cierre,
        negociacion,
        perdido
    )
}


private fun infoValidateProject(
    projects: List<Project>,
    mothLast: Int,
    currentMoth: Int
): Pair<Boolean, String> {
    var infoDiference = ""
    var mothLastCount = 0
    var mothCurrentCount = 0
    var isImproveThisCount = false


    projects.forEach {
        when (it.created.monthNumber) {
            mothLast -> mothLastCount++
            currentMoth -> mothCurrentCount++
        }
    }

    if (mothLastCount > mothCurrentCount) {
        isImproveThisCount = false
        infoDiference = "-${
            calculoPorcentual(
                mothLastCount,
                mothCurrentCount
            )
        }%"

    } else {
        isImproveThisCount = true
        infoDiference = "+${
            calculoPorcentual(
                mothLastCount,
                mothCurrentCount
            )
        }%"
    }

    return Pair(
        isImproveThisCount,
        infoDiference
    )
}