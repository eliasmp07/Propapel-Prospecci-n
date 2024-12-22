package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.selectSucursal.domain.model.Sucursale
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.getImageSucursalSelected
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.merida
import prospeccion.composeapp.generated.resources.mexico
import prospeccion.composeapp.generated.resources.monterrey
import prospeccion.composeapp.generated.resources.projects_ic_dra

@Composable
fun CardSucursalDesktop(
    modifier: Modifier = Modifier,
    sucursalId: Int,
    leads: Int,
    projects: Int,
    sucursale: Sucursale
) {

    val value by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    val colors = listOf(
        Color(0xFF405DE6),
        Color(0xFFC13584),
        Color(0xFFFD1D1D),
        Color(0xFFFFDC80)
    )

    var gradientBrush by remember {
        mutableStateOf(
            Brush.horizontalGradient(
                colors = colors,
                startX = -10.0f,
                endX = 400.0f,
                tileMode = TileMode.Repeated
            )
        )
    }

    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.drawBehind {
                    rotate(value) {
                        drawCircle(
                            gradientBrush,
                            style = Stroke(width = 12.dp.value)
                        )
                    }
                }.size(100.dp).clip(CircleShape).align(Alignment.CenterHorizontally)
            ) {
               AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = sucursale.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text(
                text = sucursale.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = sucursale.direccion,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ItemSucursalDetailInfo(
                    icon = Icons.Outlined.Person,
                    value = leads
                )
                ItemSucursalDetailInfo(
                    icon = vectorResource(Res.drawable.projects_ic_dra),
                    value = projects
                )
                ItemSucursalDetailInfo(
                    icon = Icons.Outlined.CalendarMonth,
                    value = 10
                )
            }
        }
    }
}

@Composable
fun ItemSucursalDetailInfo(
    value: Int,
    icon: ImageVector
) {
    Column {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}


