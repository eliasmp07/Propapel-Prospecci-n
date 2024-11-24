package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.Customer
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.img_no_data

@Composable
fun PieChartLeadsStatus(
    listCustomer: List<Customer>,
    modifier: Modifier = Modifier,
    size: Dp = 270.dp
) {

    var showMoreInfoNew by remember { mutableStateOf(false) }
    var showMoreInfoDesarrollo by remember { mutableStateOf(false) }
    var showMoreInfoRecuperacion by remember { mutableStateOf(false) }


    val customerNew = listCustomer.filter { it.typeClient == TypeOfClient.NUEVO.name }
    val customerDesarrollo = listCustomer.filter { it.typeClient == TypeOfClient.DESARROLLO.name }
    val customerRecuperacion = listCustomer.filter { it.typeClient == TypeOfClient.RECUPERACIÓN.name }

    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(15.dp)
    ) {
        val testPieChartData: List<PieChartData> = listOf(
            PieChartData(
                partName = "Nuevo ${customerNew.size}",
                data = customerNew.size.toDouble(), // No es necesario ?: 0.0
                color = SuccessGreen,
            ),
            PieChartData(
                partName = "En recuperación ${customerRecuperacion.size}",
                data = customerRecuperacion.size.toDouble(),
                color = Color.Red,
            ),
            PieChartData(
                partName = "En desarrollo ${customerDesarrollo.size}",
                data = customerDesarrollo.size.toDouble(),
                color = Color.Blue,
            )
        )
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth().animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Clientes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            PieChart(
                modifier = modifier.fillMaxWidth().height(size).padding(top = 8.dp).align(Alignment.CenterHorizontally),
                legendPosition = LegendPosition.DISAPPEAR,
                pieChartData = testPieChartData,
                descriptionStyle = TextStyle(fontSize = 12.sp),
                ratioLineColor = Color.Black,
                textRatioStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp
                ),
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                testPieChartData.forEach {
                    Column {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(12.dp).background(
                                    it.color,
                                    CircleShape
                                )
                            )
                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                            Text(
                                text = it.partName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                            )
                            IconButton(
                                onClick = {
                                    when (it.partName) {
                                        "Nuevo ${customerNew.size}" -> {
                                            showMoreInfoNew = !showMoreInfoNew
                                        }
                                        "En recuperación ${customerRecuperacion.size}" -> {
                                            showMoreInfoRecuperacion = !showMoreInfoRecuperacion
                                        }
                                        "En desarrollo ${customerDesarrollo.size}" -> {
                                            showMoreInfoDesarrollo = !showMoreInfoDesarrollo
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (it.partName == "Nuevo ${customerNew.size}") {
                                        if (showMoreInfoNew) {
                                            Icons.Default.ExpandLess
                                        } else {
                                            Icons.Default.ExpandMore
                                        }
                                    } else if (it.partName == "En recuperación ${customerRecuperacion.size}") {
                                        if (showMoreInfoRecuperacion) {
                                            Icons.Default.ExpandLess
                                        } else {
                                            Icons.Default.ExpandMore
                                        }
                                    } else {
                                        if (showMoreInfoDesarrollo) {
                                            Icons.Default.ExpandLess
                                        } else {
                                            Icons.Default.ExpandMore
                                        }
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        if (showMoreInfoNew && it.partName == "Nuevo ${customerNew.size}") {
                            customerNew.forEach {
                                Text(
                                    text = "Empresa: ${it.companyName}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black,
                                )
                            }
                        }
                        if (showMoreInfoRecuperacion && it.partName == "En recuperación ${customerRecuperacion.size}") {
                            customerRecuperacion.forEach {
                                Text(
                                    text = "Empresa: ${it.companyName}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }
                        }
                        if (showMoreInfoDesarrollo && it.partName == "En desarrollo ${customerDesarrollo.size}") {
                            customerDesarrollo.forEach {
                                Text(
                                    text = "Empresa: ${it.companyName}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

