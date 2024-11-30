package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SeeAllButton(image: Int,modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 18.dp, end = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
       // Image(painter = painterResource(image), contentDescription = null)
        Text(
            text = "Clientes"
        )
        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = outlinedButtonColors(
                contentColor = Color(0xFFFB7D8A)
            ),
            border = BorderStroke(1.dp, Color(0xFFFB7D8A).copy(alpha = 0.25f)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(width = 75.dp, height = 27.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Ver más", color = Color(0xFFFB7D8A), fontSize = 14.sp)
        }
    }

}