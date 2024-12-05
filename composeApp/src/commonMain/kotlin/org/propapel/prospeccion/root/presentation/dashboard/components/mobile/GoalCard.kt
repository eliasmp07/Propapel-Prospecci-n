@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package org.propapel.prospeccion.root.presentation.dashboard.components.mobile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.objective_appointment

@Composable
fun GoalCard(
    title: String,            // Título de la meta
    currentValue: Float,      // Valor actual de progreso
    goalValue: Float,         // Valor meta
    modifier: Modifier = Modifier
        .aspectRatio(16f / 7f),
    icon: ImageVector,
    background: Color,
) {
    val percentage = (currentValue / goalValue).coerceIn(0f, 1f)  // Calcular el porcentaje de avance
    val formattedPercentage = (percentage * 100).toInt()          // Formatear a porcentaje entero
    val windowClass = calculateWindowSizeClass()
    val showNavigationRail = windowClass.widthSizeClass != WindowWidthSizeClass.Compact

    Box(modifier = Modifier) {  // Asegúrate de que el modificador se pase
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            modifier = modifier
                .padding(horizontal = 8.dp),
            elevation = CardDefaults.elevatedCardElevation(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFA726),  // Naranja claro
                                Color(0xFFFF5722)
                            )
                        )
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Meta de citas",  // Mostrar el valor actual y la meta
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$currentValue / $goalValue",  // Mostrar el valor actual y la meta
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = title, fontSize = 14.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalPaperTenderProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        currentStatus = currentValue,
                        height = 9.dp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "$formattedPercentage%", fontSize = 12.sp, color = Color.White)
                }
            }
        }
        Image(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp),
            painter = painterResource(Res.drawable.objective_appointment),
            contentDescription = null
        )
    }
}

@Composable
fun HorizontalPaperTenderProgressIndicator(
    modifier: Modifier = Modifier,
    currentStatus: Float = 0f,
    goal: Float = 10.0f,
    height: Dp,
) {
    Canvas(
        modifier = modifier
            .height(height)
            .fillMaxWidth()// Ajusta el ancho según sea necesario
    ) {
        val totalWidth = size.width - 2 * 12.dp.toPx()
        val segmentWidth = totalWidth / (goal + 1) // Ajuste para 10 bolitas
        val circleRadius = 8.dp.toPx() // Hacer los círculos más pequeños
        val shadowRadius = circleRadius * 1.5f

        // Dibujar sombras detrás de los círculos
        (0 until goal.toInt()).forEachIndexed { index, _ -> // Cambiar a (0 until goal)
            val offsetX = segmentWidth * index + circleRadius
            drawCircle(
                color = Color(0xFF929292),
                radius = shadowRadius,
                center = Offset(
                    offsetX,
                    size.height / 2
                )
            )
        }

        // Dibuja la línea que será la sombra
        drawLine(
            color = Color(0xFF929292),
            start = Offset(
                circleRadius,
                size.height / 2
            ),
            end = Offset(
                size.width - circleRadius,
                size.height / 2
            ),
            strokeWidth = 13.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Línea que representa cuando no hay progreso
        drawLine(
            color = Color(0xFFFFFFFF),
            start = Offset(
                circleRadius,
                size.height / 2
            ),
            end = Offset(
                size.width - circleRadius,
                size.height / 2
            ),
            strokeWidth = 8.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Dibujar la línea de progreso
        drawLine(
            color = Color(0xFF8FF303),
            start = Offset(
                circleRadius,
                size.height / 2
            ),
            end = Offset(
                if (currentStatus == goal) size.width - circleRadius else segmentWidth * currentStatus + circleRadius,
                size.height / 2
            ),
            strokeWidth = 8.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Dibujar los círculos principales
        (0 until goal.toInt()).forEachIndexed { index, _ -> // Cambiar a (0 hasta goal)
            // Llenar el círculo solo si se ha alcanzado el índice correspondiente
            val color = if (currentStatus >= index + 1) Color(0xFF8FF303) else Color.White
            val offsetX = segmentWidth * index + circleRadius
            drawCircle(
                color = color,
                radius = circleRadius,
                center = Offset(
                    offsetX,
                    size.height / 2
                )
            )
        }
    }
}
