@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.leads.components.desktop


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.root.presentation.leads.LeadAction
import org.propapel.prospeccion.root.presentation.leads.LeadSMState


@Composable
fun LeadScreenDesktop(
    onRefresh: () -> Unit = {},
    state: LeadSMState,
    onAction: (LeadAction) -> Unit
) {
    val lazyColumnState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { onRefresh() }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
           /*
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().height(500.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxHeight()
                    ) {
                        // Gráfico de producto con más interés
                        val orders = state.customers.flatMap { it.purchase }
                        if (orders.isNotEmpty()) {
                            DashboardChart(
                                title = "Producto con más interés",
                                orders = orders,
                                products = provideProductsPropapel()
                            )
                        }

                    }
                    // Sección de gráficos
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxHeight().padding(vertical = 16.dp)
                    ) {
                        // Gráfico de interacciones de clientes
                        if (state.customers.isNotEmpty()) {
                            DonutChartInteractions(
                                modifier = Modifier.fillMaxWidth(),
                                customers = state.customers
                            )
                        }

                        // Gráfico de estados de leads
                        PieChartLeadsStatus(
                            modifier = Modifier.size(300.dp),
                            listCustomer = state.customers,
                            size = 300.dp
                        )
                    }

                    // Sección de lista de clientes
                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxHeight()
                            .background(Color.White, shape = RoundedCornerShape(20.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Mis clientes",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        if (state.customers.isEmpty()) {
                            // Estado vacío
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No tienes leads registrados",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        } else {
                            val pageSize = 10  // Mostrar más elementos en pantalla grande
                            val totalCustomers = state.customers.size
                            val totalPages = (totalCustomers + pageSize - 1) / pageSize
                            var page by remember { mutableIntStateOf(1) }

                            // Controles de navegación de página
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { if (page > 1) page-- }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Página $page de $totalPages",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = { if (page < totalPages) page++ }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = null,
                                        modifier = Modifier.rotate(180f)
                                    )
                                }
                            }

                            // Mostrar clientes de la página actual
                            val startIndex = (page - 1) * pageSize
                            val endIndex = minOf(startIndex + pageSize, totalCustomers)
                            val customersToShow = state.customers.subList(startIndex, endIndex)

                            LazyColumn(
                                state = lazyColumnState,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(customersToShow) { customer ->
                                    SwipeableItemWithActions(
                                        isRevealed = false,
                                        onExpanded = { /* Acción cuando se expande */ },
                                        onCollapsed = { /* Acción cuando se colapsa */ },
                                        actions = {
                                            ActionIcon(
                                                onClick = { onAction(LeadAction.OnUpdateLeadClick(customer.idCustomer.toString())) },
                                                backgroundColor = MaterialTheme.colorScheme.primary,
                                                icon = Icons.Default.Update,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp))
                                            )
                                            ActionIcon(
                                                onClick = { onAction(LeadAction.OnToggleCreateAppointmentDialog(customer.idCustomer)) },
                                                backgroundColor = SuccessGreen,
                                                icon = Icons.Default.CalendarMonth,
                                                tint = Color.White,
                                                modifier = Modifier.fillMaxHeight()
                                            )
                                        },
                                    ) {
                                        ItemLead(
                                            customer = customer,
                                            onClick = { onAction(LeadAction.OnDetailLeadClick(it)) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            */
        }
        // Botón flotante para agregar cliente
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            icon = {
                Icon(
                    imageVector = Icons.Filled.PersonAddAlt,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            text = { Text("Agregar cliente", color = Color.White) },
            onClick = { onAction(LeadAction.OnAddLeadClick) }
        )
    }
}
