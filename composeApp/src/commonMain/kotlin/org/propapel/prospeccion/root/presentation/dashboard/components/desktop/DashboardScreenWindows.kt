package org.propapel.prospeccion.root.presentation.dashboard.components.desktop

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.CalendarDatesCard
import org.propapel.prospeccion.core.presentation.designsystem.components.DashboardCard
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMAction
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMState
import org.propapel.prospeccion.root.presentation.dashboard.components.mobile.Banner
import org.propapel.prospeccion.root.presentation.dashboard.components.mobile.BannerPaggerWindows
import org.propapel.prospeccion.root.presentation.dashboard.components.mobile.GoalCard
import org.propapel.prospeccion.root.presentation.leads.UiState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date
import prospeccion.composeapp.generated.resources.customer_person

data class ItemScreen(
    val content: @Composable () -> Unit
)

private fun providesItemsScreen(
    state: DashboardSMState,
    onAction: (DashboardSMAction) -> Unit
): List<ItemScreen>{
    return listOf(
        ItemScreen {
            if (state.myCustomer is UiState.Success){

                val customerNew = state.myCustomer.value.filter { it.typeClient == TypeOfClient.NUEVO.name }
                val customerDesarrollo = state.myCustomer.value.filter { it.typeClient == TypeOfClient.DESARROLLO.name }
                val customerRecuperacion = state.myCustomer.value.filter { it.typeClient == TypeOfClient.RECUPERACIÓN.name }

                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(15.dp)
                ) {
                    val testPieChartData: List<PieChartData> = listOf(
                        PieChartData(
                            partName = "Nuevo ${customerNew.size}",
                            data = customerNew.size.toDouble(), // No es necesario ?: 0.0
                            color = SuccessGreen,
                        ),
                        PieChartData(
                            partName = "En recuperación ${customerRecuperacion.size}",
                            data = customerRecuperacion.size.toDouble(),
                            color =Color(0XFFf6a13e),
                        ),
                        PieChartData(
                            partName = "En desarrollo ${customerDesarrollo.size}",
                            data = customerDesarrollo.size.toDouble(),
                            color = Color(0XFF3e93f6),
                        )
                    )
                    Column(
                        modifier = Modifier.padding(8.dp).fillMaxWidth().animateContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Clientes",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        PieChart(
                            modifier = Modifier.size(300.dp).padding(top = 8.dp).align(Alignment.CenterHorizontally),
                            legendPosition = LegendPosition.DISAPPEAR,
                            pieChartData = testPieChartData,
                            descriptionStyle = TextStyle(fontSize = 12.sp),
                            ratioLineColor = Color.Black,
                            textRatioStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp
                            ),
                        )}}
            }
        },
        ItemScreen {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
            ){
                ElevatedCard(
                    modifier = Modifier.weight(0.5f).pointerHoverIcon(PointerIcon.Hand),
                    shape = RoundedCornerShape(
                        30.dp
                    ),
                    onClick = {
                        onAction(DashboardSMAction.OnMoveLeadScreenClick)
                    }
                ) {
                    Box{
                        Column(
                            modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Agregar lead",
                                style = MaterialTheme.typography.titleMedium,

                            )
                        }
                        Image(
                            modifier = Modifier.size(
                                100.dp
                            ).align(Alignment.BottomEnd),
                            painter = painterResource(Res.drawable.customer_person),
                            contentDescription = "customer_person",
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                ElevatedCard(
                    modifier = Modifier.weight(0.5f).pointerHoverIcon(PointerIcon.Hand),
                    shape = RoundedCornerShape(
                        30.dp
                    ),
                    onClick = {
                        onAction(DashboardSMAction.OnCreateReminderClick)
                    }
                ) {
                    Box{
                        Column(
                            modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Crear cita",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                        Image(
                            modifier = Modifier.size(
                                100.dp
                            ).align(Alignment.BottomEnd),
                            painter = painterResource(Res.drawable.calendar_date),
                            contentDescription = "customer_person",
                        )
                    }
                }
            }
        },
        ItemScreen {
            CalendarDatesCard(
                modifier = Modifier.padding(8.dp),
                state = state,
                onAction = onAction
            )
        }
    )
}
@Composable
fun DashboardScreenWindows(
    state: DashboardSMState,
    user: AuthInfo,
    onAction: (DashboardSMAction) -> Unit
) {
    val items = providesItemsScreen(state, onAction)
    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        )
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(16.dp),
            columns = StaggeredGridCells.Fixed(2),
        ){
            item(
                span = StaggeredGridItemSpan.FullLine
            ){
                BannerPaggerWindows(
                    items = listOf(
                        Pair(
                            null
                        ) {
                            GoalCard(
                                modifier = Modifier.fillMaxWidth().height(500.dp),
                                title = "Mi objetivo",
                                currentValue = state.totalRemindersMoth.toFloat(),
                                goalValue = 10f,
                                icon = Icons.Filled.Flag,
                                background = SuccessGreen
                            )
                        },
                        Pair(
                            Banner(
                                id = 2,
                                description = "Oferta especial",
                                imageUrl = "https://firebasestorage.googleapis.com/v0/b/prosales-c49e5.appspot.com/o/SAI%20(2)-1200x400.jpg?alt=media&token=8b021601-63fc-4c9b-872e-0687c2610da0"
                            )
                        ) {},
                        Pair(
                            Banner(
                                id = 3,
                                description = "Otro descuento especial",
                                imageUrl = "https://firebasestorage.googleapis.com/v0/b/prosales-c49e5.appspot.com/o/banner_prueba.jpg?alt=media&token=ebc7287c-5c11-48ca-a354-e89f0864d2ae"
                            )
                        ) {
                        },
                    )
                )
            }
            item(
                span = StaggeredGridItemSpan.FullLine
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp).animateContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (state.myCustomer is UiState.Success){

                        val listCustomer = state.myCustomer
                        val customerNew = listCustomer.value.filter { it.typeClient == TypeOfClient.NUEVO.name }
                        val customerDesarrollo = listCustomer.value.filter { it.typeClient == TypeOfClient.DESARROLLO.name }
                        val customerRecuperacion = listCustomer.value.filter { it.typeClient == TypeOfClient.RECUPERACIÓN.name }

                        DashboardCard(
                            title = "Clientes Totales",
                            value = state.myCustomer.value.size.toString(),
                            percentage ="${ ((state.myCustomer.value.size* 100) / listCustomer.value.size)} %",
                            modifier = Modifier.weight(1f),
                            icon = Icons.Outlined.Groups,
                            background = Color(0XFF723bde)
                        )
                        DashboardCard(
                            title = "Clientes Nuevos",
                            value = customerNew.size.toString(),
                            percentage = "${((customerNew.size * 100) / listCustomer.value.size)} %",
                            modifier = Modifier.weight(1f),
                            icon = Icons.Outlined.Person,
                            customers = customerNew,
                            background = SuccessGreen
                        )
                        DashboardCard(
                            title = "Clientes en Desarrollo",
                            value = customerDesarrollo.size.toString(),
                            percentage = "${((customerDesarrollo.size * 100) / listCustomer.value.size)} %",
                            modifier = Modifier.weight(1f),
                            icon = Icons.Outlined.Person,
                            customers = customerDesarrollo,
                            background = Color(0XFF3e93f6)
                        )
                        DashboardCard(
                            title = "Clientes en Recuperación",
                            value = customerRecuperacion.size.toString(),
                            percentage = "${((customerRecuperacion.size * 100) / listCustomer.value.size)} %",
                            modifier = Modifier.weight(1f),
                            customers = customerRecuperacion,
                            icon = Icons.Outlined.Person,
                            background = Color(0XFFf6a13e)
                        )
                    }
                }
            }
            items(items) {
                it.content()
            }
        }
    }
}