@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.leads.components.desktop


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.root.presentation.createReminder.components.DialogDayNoAvailable
import org.propapel.prospeccion.root.presentation.dashboard.components.DonutChartInteractions
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.root.presentation.leads.LeadAction
import org.propapel.prospeccion.root.presentation.leads.LeadSMState
import org.propapel.prospeccion.root.presentation.leads.UiState
import org.propapel.prospeccion.root.presentation.leads.components.ActionIcon
import org.propapel.prospeccion.root.presentation.leads.components.SwipeableItemWithActions
import org.propapel.prospeccion.root.presentation.leads.components.mobile.BarCharProducts
import org.propapel.prospeccion.root.presentation.leads.components.mobile.BarCharProjects
import org.propapel.prospeccion.root.presentation.leads.components.mobile.CreateReminderLeadDialog
import org.propapel.prospeccion.root.presentation.leads.components.mobile.ItemLead
import org.propapel.prospeccion.root.presentation.leads.components.mobile.PaginationControls


@Composable
fun LeadScreenDesktop(
    onRefresh: () -> Unit = {},
    state: LeadSMState,
    onAction: (LeadAction) -> Unit
) {
    val lazyColumnState = rememberLazyStaggeredGridState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { onRefresh() }
    )

    var page by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier.fillMaxSize()
            .pullRefresh(pullRefreshState).background(
                Brush.verticalGradient(
                    0f to PrimaryYellowLight,
                    0.6f to SoporteSaiBlue30,
                    1f to MaterialTheme.colorScheme.primary
                )
            )
    ) {
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Sidebar o menú lateral
            /*
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Menú",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Agrega ítems de navegación
                    Text(text = "Clientes")
                    Text(text = "Gráficos")
                }
            }

             */

            LazyColumn(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
            ){
                item {
                    GenericContentLoading(
                        modifier = Modifier.height(600.dp),
                        data = state.project,
                        retry = {
                            onAction(LeadAction.OnRetryProject)
                        },
                        success = {
                            BarCharProjects(
                                modifier = Modifier.height(600.dp),
                                projects = it
                            )
                        }
                    )
                }
                item {
                    GenericContentLoading(
                        modifier = Modifier.height(500.dp),
                        data = state.remindersList,
                        success = {
                            DonutChartInteractions(
                                modifier = Modifier.weight(1f).height(500.dp),
                                heightChar = 500.dp,
                                reminders = it
                            )
                        },
                        retry = {

                        }
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
            ) {
                LazyVerticalStaggeredGrid(
                    state = lazyColumnState,
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp).padding(horizontal = 16.dp)
                ) {
                    // Clientes listados
                    if (state.customers is UiState.Success) {
                        val pageSize = 6
                        val totalCustomers = state.customers.value.size
                        val totalPages = (totalCustomers + pageSize - 1) / pageSize

                        // Sublista de clientes para la página actual
                        val startIndex = (page - 1) * pageSize
                        val endIndex = minOf(
                            startIndex + pageSize,
                            totalCustomers
                        )
                        val customersToShow = state.customers.value.subList(
                            startIndex,
                            endIndex
                        )

                        item(
                            span = StaggeredGridItemSpan.FullLine
                        ) {
                            PaginationControls(
                                modifier = Modifier.padding(16.dp),
                                page = page,
                                totalPages = totalPages,
                                onPreviousPage = { if (page > 1) page-- },
                                onNextPage = { if (page < totalPages) page++ }
                            )
                        }
                        items(
                            customersToShow,
                            key = { it.idCustomer }) { customer ->
                            SwipeableItemWithActions(
                                modifier = Modifier.padding(horizontal = 16.dp),
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
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 30.dp,
                                                    bottomStart = 30.dp
                                                )
                                            )
                                    )
                                    ActionIcon(
                                        onClick = { onAction(LeadAction.OnToggleCreateAppointmentDialog(customer.idCustomer)) },
                                        backgroundColor = SuccessGreen,
                                        icon = Icons.Default.CalendarMonth,
                                        tint = Color.White,
                                        modifier = Modifier.fillMaxHeight()
                                    )
                                }
                            ) {
                                ItemLead(
                                    modifier = Modifier,
                                    customer = customer,
                                    onClick = { onAction(LeadAction.OnDetailLeadClick(it)) }
                                )
                            }
                        }
                    }
                    item (
                        span = StaggeredGridItemSpan.FullLine
                    ){
                        // Sección de gráficos
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            GenericContentLoading(
                                modifier = Modifier.weight(1f).height(600.dp),
                                data = state.productInteres,
                                success = {
                                    BarCharProducts(
                                        modifier = Modifier.weight(1f).height(300.dp),
                                        products = it
                                    )
                                },
                                retry = {

                                }
                            )
                        }
                    }
                }

            }
        }
    }

    if (state.dateNoAvailable){
        DialogDayNoAvailable {
            onAction(LeadAction.OnDismissDialogDayNoAvailable)
        }
    }

    if (state.showCreateDate) {
        CreateReminderLeadDialog(
            state = state,
            onAction = onAction,
            onDismissRequest = {
                onAction(LeadAction.OnToggleCreateAppointmentDialog(0))
            }
        )
    }

}
