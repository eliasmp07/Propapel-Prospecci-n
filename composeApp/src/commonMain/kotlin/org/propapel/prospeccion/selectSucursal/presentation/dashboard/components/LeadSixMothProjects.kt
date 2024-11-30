package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

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
import org.propapel.prospeccion.core.presentation.ui.extensions.previewTwoMoth
import org.propapel.prospeccion.core.presentation.ui.extensions.previousMoth
import org.propapel.prospeccion.root.presentation.dashboard.components.monthGet
import org.propapel.prospeccion.selectSucursal.domain.model.CustomerUser
import org.propapel.prospeccion.selectSucursal.domain.model.ProjectUser

@Composable
fun LeadSixMothProjects(
    modifier: Modifier,
    projects: List<ProjectUser>
) {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val previousMonthDate = currentDate.previousMoth()

    val (isLow, diffence) = infoValidateProjects(
        projects = projects,
        currentMoth = currentDate.monthNumber,
        mothLast = previousMonthDate
    )

    val (closeLast, negotiationLast, lossLast) =
        countTotalProjectsByState(
            projects,
            previousMonthDate
        )
    val (closeNow, negotiationNow, lossNow) =
        countTotalProjectsByState(
            projects,
            currentDate.monthNumber
        )

    val barColors = listOf(
        Color.Red,
        Color.Gray,
        Color(0xFFFF9800)
    )

    val testBarParameters: List<BarParameters> = listOf(
        BarParameters(
            "Cerrados",
            listOf(
                closeLast.toDouble(),
                closeNow.toDouble()
            ),
            barColors[1]
        ),
        BarParameters(
            "Perdidos",
            listOf(
                lossLast.toDouble(),
                lossNow.toDouble()
            ),
            barColors[0]
        ),
        BarParameters(
            "En negociación",
            listOf(
                negotiationLast.toDouble(),
                negotiationNow.toDouble()
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
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Proyectos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                ContentValidateLastMoth(
                    isLow = !isLow,
                    diffence
                )
            }
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

fun countTotalProjectsByState(
    projects: List<ProjectUser>,
    month: Int,
): Triple<Int, Int, Int>{
    var close = 0
    var negotiation = 0
    var loss = 0

    projects.forEach { project ->
        if (project.createdAt.monthNumber == month){
            when (project.status) {
                "Cierre" -> close++
                "Perdido" -> loss++
                else -> negotiation++
            }
        }
    }
    return Triple(
        close,
        negotiation,
        loss
    )
}

fun infoValidateProjects(
    projects: List<ProjectUser>,
    mothLast: Int,
    currentMoth: Int
): Pair<Boolean, String> {
    var infoDiference = ""
    var mothLastCount = 0
    var mothCurrentCount = 0
    var isImproveThisCount = false

    projects.forEach {
        when (it.createdAt.monthNumber) {
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