package org.propapel.prospeccion.selectSucursal.presentation.selectHome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateAttention
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo
import prospeccion.composeapp.generated.resources.merida
import prospeccion.composeapp.generated.resources.mexico
import prospeccion.composeapp.generated.resources.monterrey


@Composable
fun ItemSucusal(
    sucusales: Sucusales,
    selected: Boolean,
    onSelect: (Int) -> Unit
) {
    val colors = listOf(
        Color(0xFF405DE6),
        Color(0xFFC13584),
        Color(0xFFFD1D1D),
        Color(0xFFFFDC80)
    )

    val gradientBrush = remember {
        Brush.horizontalGradient(colors)
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .size(width = 300.dp, height = 400.dp)
            .then(
                if (selected) Modifier.animateAttention(
                    initialValue = 0.5f
                )
                    .background(brush = gradientBrush, shape = RoundedCornerShape(16.dp))
                    .padding(6.dp)
                else Modifier
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxSize(),
            onClick = {
                onSelect(sucusales.id)
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                ){
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        painter = painterResource(getImageSucursalSelected(sucusal = sucusales.sucursal) ?:Res.drawable.monterrey),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier.padding(16.dp).size(height = 50.dp, width = 80.dp).align(Alignment.TopStart),
                        painter = painterResource(Res.drawable.logo),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                }
                Text(
                    text = sucusales.sucursal,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


fun getImageSucursalSelected(sucusal: String): DrawableResource?{
    return when{
        sucusal.contains("Propapel Merida") -> Res.drawable.merida
        sucusal.contains("Propapel Monterrey") -> Res.drawable.monterrey
        sucusal.contains("Propapel Mexico") -> Res.drawable.mexico
        else -> null
    }
}

