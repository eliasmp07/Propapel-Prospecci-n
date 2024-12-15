package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person

@Composable
fun CardInfoDesktop(
    title: String,
    value: Int,
    imageTypeCard: DrawableResource,
) {
    ElevatedCard(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp)
        ){
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HeaderCardInfoDesktop(
                    title = title,
                    count = value,
                )
            }
            Image(
                modifier = Modifier.align(Alignment.BottomEnd).size(80.dp).padding(8.dp),
                painter = painterResource(imageTypeCard),
                contentDescription = null
            )
        }

    }
}

@Composable
private fun HeaderCardInfoDesktop(
    modifier: Modifier = Modifier,
    title: String,
    count: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun ContentCardInfoDesktop(
    modifier: Modifier = Modifier,
    value: Int
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )
    }
}
