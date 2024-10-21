package org.propapel.prospeccion.core.presentation.designsystem.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    size: Dp = 600.dp
) {

    if (listCustomer.isEmpty()) {
        // Mostrar un mensaje o gráfico vacío si no hay clientes
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
        ) {
            Column(
                modifier =Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Estadistica de tipos de clientes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007BFF)
                )
                Image(
                    painter = painterResource(Res.drawable.img_no_data),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text("No tienes clientes")
            }
        }
        return
    }


    val customerNew = listCustomer.filter { it.typeClient == TypeOfClient.NUEVO.name }
    val customerDesarrollo = listCustomer.filter { it.typeClient == TypeOfClient.DESARROLLO.name }
    val customerRecuperacion = listCustomer.filter { it.typeClient == TypeOfClient.RECUPERACION.name }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
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
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = "Estadistica de tipos de clientes",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        PieChart(
            modifier = modifier.size(size).padding(top = 8.dp).align(Alignment.CenterHorizontally),
            legendPosition = LegendPosition.BOTTOM,
            pieChartData = testPieChartData,
            descriptionStyle = TextStyle(fontSize = 8.sp),
            ratioLineColor = Color.LightGray,
            textRatioStyle = TextStyle(color = Color.Gray, fontSize = 10.sp),
        )
    }


}
