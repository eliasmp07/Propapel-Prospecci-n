package org.propapel.prospeccion.root.presentation.dashboard.components.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun BannerItemMobileScreen(
    modifier: Modifier = Modifier.aspectRatio(16f / 7f),
    banner: Banner,
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        onClick = {
        }
    ) {
        if (banner.imageUrl.isNotEmpty()){
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = banner.imageUrl,
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }else{
            Column(
                modifier = Modifier // Alinear el texto en la parte inferior izquierda
                    .padding(16.dp) // Espaciado interno
                    .background(Color.Black.copy(alpha = 0.5f)) // Fondo semi-transparente para mejorar la legibilidad
            ) {
                Text(
                    text = banner.description,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium// Estilo de texto
                )
                if (banner.discountPercentage != null) {
                    Text(
                        text = "${banner.discountPercentage}% de descuento",
                        color = Color.Yellow,
                        style = MaterialTheme.typography.bodyMedium// Estilo de texto
                    )
                }
                Text(
                    text = "Válido hasta: ${banner.endDate}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium // Estilo de texto
                )
            }
        }
    }
}