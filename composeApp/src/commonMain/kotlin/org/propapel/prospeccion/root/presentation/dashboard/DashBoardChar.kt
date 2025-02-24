package org.propapel.prospeccion.root.presentation.dashboard

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
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.presentation.createProject.componetns.ProductPropapel
import org.propapel.prospeccion.root.presentation.leads.components.mobile.sumTotalSaleForProducts

fun aggregateSalesDataByCategory(
    purchases: List<Purchase>,
    products: List<ProductPropapel>,
): Map<String, Double> {
    val categoryMap = products.associate { it.name to it.name }

    val categorySales = mutableMapOf<String, Double>().withDefault { 0.0 }


    purchases.forEach { purchase ->
        val purchaseTitle = categoryMap[purchase.productServiceName]
        if (purchaseTitle != null) {
            categorySales[purchaseTitle] =
                categorySales.getValue(purchaseTitle) + purchase.amount.toDouble()
        }
    }

    return categorySales
}

@Composable
fun DashboardChart(
    title: String, products: List<Purchase>,
    modifier: Modifier = Modifier
) {
    val barParameters: MutableList<LineParameters> = mutableListOf()
    val productsWithAmout = sumTotalSaleForProducts(products)
    val xAxisData:MutableList<String> = mutableListOf()
    productsWithAmout.forEach {
        LineParameters(
            label = it.key,
            data = listOf(it.value),
            lineColor = Color(0xFFF24C9D),
            lineType = LineType.CURVED_LINE,
            lineShadow = false
        )
        xAxisData.add(it.key.take(3))
    }
    /*  val categorySalesPercentage = aggregateSalesDataByCategory(orders, products)
        .mapValues { (it.value / orders.sumOf { order -> order.amount.toDouble() }) * 100 }


    val xAxisData:MutableList<String> = mutableListOf()
    products.forEach {
        xAxisData.add(it.name.take(4))
    }
    val xAxisDataPoints = xAxisData.size

    val testLineParameters: List<LineParameters> =
        categorySalesPercentage.entries.mapIndexed { index, entry ->
            LineParameters(
                label = entry.key,
                data = List(xAxisDataPoints) { entry.value * (0.5 + 0.5 * it / (xAxisDataPoints - 1)) },
                lineColor = when (index) {
                    0 -> Color(0xFF22A699)
                    1 -> Color(0xFFF2BE22)
                    2 -> Color(0xFFF29727)
                    3 -> Color(0xFFF24C3D)
                    4 -> Color(0xFFF24C9D)
                    else -> Color.Gray
                },
                lineType = LineType.CURVED_LINE,
                lineShadow = true,
            )
        }

     */


    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(16.dp).fillMaxWidth(),
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
            }
            Spacer(modifier = Modifier.height(8.dp))

            LineChart(
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp),
                descriptionStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.W400
                ),
                linesParameters = barParameters,
                isGrid = true,
                gridColor = Color.Blue,
                xAxisData = xAxisData,
                animateChart = true,
                showGridWithSpacer = true,
                yAxisStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                ),
                xAxisStyle = TextStyle(
                    fontSize = 12.sp,
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