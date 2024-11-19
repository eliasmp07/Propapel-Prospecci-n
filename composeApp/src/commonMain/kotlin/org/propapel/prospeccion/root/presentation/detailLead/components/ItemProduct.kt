package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPink
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.root.domain.models.Purchase
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.ic_product_otline

@Composable
fun ItemProduct(
    modifier: Modifier = Modifier,
    purchase: Purchase
) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side with the gradient and month
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PrimaryYellowLight,
                                PrimaryPink
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_product_otline),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Producto: ${purchase.productServiceName}",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }
    }
}
