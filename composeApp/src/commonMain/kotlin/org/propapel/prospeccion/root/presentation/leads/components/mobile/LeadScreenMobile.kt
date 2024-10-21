@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.leads.components.mobile

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.components.CalendarDatesCard
import org.propapel.prospeccion.core.presentation.designsystem.components.PieChartLeadsStatus
import org.propapel.prospeccion.core.presentation.designsystem.components.SalesPeopleCard
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProductsPropapel
import org.propapel.prospeccion.root.presentation.dashboard.DashboardChart
import org.propapel.prospeccion.root.presentation.dashboard.DonutChartServices
import org.propapel.prospeccion.root.presentation.dashboard.components.DonutChartInteractions
import org.propapel.prospeccion.root.presentation.leads.LeadAction
import org.propapel.prospeccion.root.presentation.leads.LeadSMState
import org.propapel.prospeccion.root.presentation.leads.components.MonthlyVisitsChart
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.img_no_data

@Composable
fun LeadScreenMobile(
    onRefresh: () -> Unit = {},
    state: LeadSMState,
    onAction: (LeadAction) -> Unit
) {

    val lazyColumState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(state.isRefreshing, { onRefresh()})
    Box(
        modifier = Modifier.fillMaxSize()
            .pullRefresh(pullRefreshState)
    ){
        LazyColumn(
            state = lazyColumState,
            modifier = Modifier
                .background(Color(0XFFe5f0f9))
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Clientes potenciales (Leads)",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF007BFF)
                        )
                        Text(
                            "",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            val ordes = state.customers.flatMap {
                it.purchase
            }
            if (ordes.isNotEmpty()){
                item {
                    DashboardChart(
                        title = "Producto mayor vendido",
                        orders = ordes,
                        products = listOf(
                            ProductsPropapel.ROLLITOS,
                            ProductsPropapel.INSTALACION_RACKS,
                            ProductsPropapel.INSTALACION_CAMARA,
                            ProductsPropapel.RENTA_IMPRESORA,
                            ProductsPropapel.RENTA_EQUIPO_DE_COMPUTO,
                            ProductsPropapel.PAPELERIA,
                            ProductsPropapel.TOTAL_OFFICE
                        )
                    )
                }
            }
            item {
                PieChartLeadsStatus(
                    modifier = Modifier,
                    listCustomer = state.customers,
                    size = 300.dp
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mis clientes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007BFF)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (state.customers.isEmpty()){
                item {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
                    ) {
                        Column(
                            modifier = Modifier.fillParentMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.img_no_data),
                                contentDescription = null,
                                modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
                            )
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                            Text("No tienes clientes agregados")

                        }
                    }
                }
            }else{
                items(state.customers, key = {it.idCustomer}){custumer ->
                    ItemLead(
                        customer = custumer,
                        onClick = {
                            onAction(LeadAction.OnDetailLeadClick(it))
                        }
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DonutChartInteractions(Modifier, state.customers)
                }
            }
        }
        ExtendedFloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            icon = {
                Icon(
                    imageVector = Icons.Filled.PersonAddAlt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            expanded = lazyColumState.isScrolled(),
            onClick = {
                onAction(LeadAction.OnAddLeadClick)
            },
            text = {
                Text(text = "Agregar lead")
            }
        )
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun LazyListState.isScrolled(): Boolean{
    var previousIndex by remember(this) {
        mutableIntStateOf(firstVisibleItemIndex)
    }
    var previousScrollOffset by remember(this) {
        mutableIntStateOf(firstVisibleItemScrollOffset)
    }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

