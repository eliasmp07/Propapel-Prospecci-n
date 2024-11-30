package org.propapel.prospeccion.root.presentation.createProject.componetns

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectAction
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectState
import org.propapel.prospeccion.root.presentation.createProject.formatString
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.ic_product_otline
import prospeccion.composeapp.generated.resources.project_confirm

@Composable
fun ConfirmProjectIsCorrect(
    state: CreateProjectState,
    onAction: (CreateProjectAction) -> Unit
) {
    val value = state.productsProject.sumOf {
        it.amount.toDouble()
    }
    val heightSize = 320.dp
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(0.9f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp
                        )
                        .height(320.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                )
                {

                    val resultSumString = formatString(value)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightSize)
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 24.dp,
                                bottom = 8.dp,
                            )
                            .background(Color.Transparent),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Confirma si la informacion cargada es correcta",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        Image(
                            modifier = Modifier.size(100.dp),
                            painter = painterResource(Res.drawable.project_confirm),
                            contentDescription = null
                        )
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "Nombre del proyecto:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Estado del proyecto:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Prioridad del proyecto:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Valor del proyecto:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                            Column {
                                Text(
                                    text = "${state.nameProject}",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                Text(
                                    text = "${state.stateProject}",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                Text(
                                    text = "${state.priorityProject}",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                Text(
                                    text = "$$resultSumString",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                    }

                }
            }
            items(
                state.productsProject
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "Producto",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        Text(
                            text = it.productServiceName
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_product_otline),
                            contentDescription = null
                        )
                    },
                    trailingContent = {
                        Text(
                            text = "$${it.amount}"
                        )
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            ProSalesActionButton(
                text = "Confirmar",
                isLoading = state.isCreatingProduct,
                textColor = Color.White,
                onClick = {
                    onAction(CreateProjectAction.OnCreateProject(value))
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            ProSalesActionButtonOutline(
                text = "Regresar",
                onClick = {
                    onAction(CreateProjectAction.OnNextScreen(CreateProjectScreenState.PRODUCTS_CHANGE))
                }
            )
        }
    }

}

class TicketShapeVertically(
    private val circleRadius: Dp,
    private val cornerSize: CornerSize
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = getPath(
                size,
                density
            )
        )
    }

    private fun getPath(
        size: Size,
        density: Density
    ): Path {
        val roundedRect = RoundRect(
            size.toRect(),
            CornerRadius(
                cornerSize.toPx(
                    size,
                    density
                )
            )
        )
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(
            operation = PathOperation.Intersect,
            path1 = roundedRectPath,
            path2 = getTicketPath(
                size,
                density
            )
        )
    }

    private fun getTicketPath(
        size: Size,
        density: Density
    ): Path {
        val middleY = size.height.div(other = 1.5f)
        val circleRadiusInPx = with(density) { circleRadius.toPx() }

        return Path().apply {
            reset()

            lineTo(
                x = 0F,
                y = 0F
            )

            lineTo(
                x = size.width,
                y = 0F
            )

            lineTo(
                x = size.width,
                y = middleY
            )

            arcTo(
                rect = Rect(
                    left = size.width - circleRadiusInPx,
                    top = middleY - circleRadiusInPx,
                    right = size.width + circleRadiusInPx,
                    bottom = middleY + circleRadiusInPx
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = -180F,
                forceMoveTo = false
            )

            lineTo(
                x = size.width,
                y = size.height
            )

            lineTo(
                0F,
                size.height
            )

            lineTo(
                x = 0F,
                y = size.height
            )
            arcTo(
                rect = Rect(
                    left = 0F - circleRadiusInPx,
                    top = middleY - circleRadiusInPx,
                    right = circleRadiusInPx,
                    bottom = middleY + circleRadiusInPx
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -180F,
                forceMoveTo = false
            )

            lineTo(
                x = 0F,
                y = 0F
            )
        }
    }
}
