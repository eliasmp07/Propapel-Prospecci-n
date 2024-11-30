package org.propapel.prospeccion.root.presentation.leads.components.mobile

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.presentation.createProject.componetns.ProductPropapel
import org.propapel.prospeccion.root.presentation.createProject.componetns.provideProductsPropapel
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.ic_product_otline
import kotlin.random.Random

@Composable
fun BarCharProducts(
    modifier: Modifier = Modifier,
    products: List<Purchase>
) {
    val barColors = listOf(
        Color(0xFF22A699),
        Color(0xFFF2BE22),
        Color(0xFFF29727),
        Color(0xFFF24C3D),
        Color(0xFFF24C9D)
    )
    val barParameters: MutableList<BarParameters> = mutableListOf()
    val productsWithAmout = sumTotalSaleForProducts(products)
    productsWithAmout.forEach {
        barParameters.add(
            BarParameters(
                dataName = it.key,
                listOf(
                    it.value
                ),
                barColors[Random.nextInt(0,4)]
            )
        )
    }
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
                    imageVector = vectorResource(Res.drawable.ic_product_otline),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Productos mas cotizados",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            BarChart(
                chartParameters = barParameters,
                gridColor = Color.DarkGray,
                xAxisData = listOf(
                    ""
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

fun sumTotalSaleForProducts(
    products: List<Purchase>,
    productsPropapel: List<ProductPropapel> = provideProductsPropapel()
): Map<String, Double> {
    val productTitleService = productsPropapel.associate { it.name to it.name }
    val productAmout = mutableMapOf<String, Double>().withDefault { 0.0 }
    products.forEach { product ->
        val productTitle = productTitleService[product.productServiceName]
        if (productTitle != null) {
            productAmout[productTitle] = productAmout.getValue(productTitle) + product.amount.toDouble()
        }
    }
    return productAmout
}