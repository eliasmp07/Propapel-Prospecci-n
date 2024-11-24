@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.leads.components.mobile

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.PieChartLeadsStatus
import org.propapel.prospeccion.root.presentation.createProject.componetns.provideProductsPropapel
import org.propapel.prospeccion.root.presentation.createReminder.components.DialogDayNoAvailable
import org.propapel.prospeccion.root.presentation.dashboard.DashboardChart
import org.propapel.prospeccion.root.presentation.dashboard.components.DonutChartInteractions
import org.propapel.prospeccion.root.presentation.detailLead.components.CreateReminderDialog
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.root.presentation.leads.LeadAction
import org.propapel.prospeccion.root.presentation.leads.LeadSMState
import org.propapel.prospeccion.root.presentation.leads.State
import org.propapel.prospeccion.root.presentation.leads.components.ActionIcon
import org.propapel.prospeccion.root.presentation.leads.components.SwipeableItemWithActions
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.img_no_data

@Composable
fun LeadScreenMobile(
    onRefresh: () -> Unit = {},
    state: LeadSMState,
    onAction: (LeadAction) -> Unit
) {

    val lazyColumState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        state.isRefreshing,
        { onRefresh() })


    var page by remember { mutableStateOf(1) } // Usar mutableStateOf para recomposición

    Box(
        modifier = Modifier.fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            state = lazyColumState,
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        0f to PrimaryYellowLight,
                        0.6f to SoporteSaiBlue30,
                        1f to MaterialTheme.colorScheme.primary
                    )
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier.height(150.dp).fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(120.dp).shadow(
                            2.dp,
                            RoundedCornerShape(
                                bottomEnd = 30.dp,
                                bottomStart = 30.dp
                            )
                        ).background(
                            color = PrimaryYellowLight,
                            shape = RoundedCornerShape(
                                bottomEnd = 30.dp,
                                bottomStart = 30.dp
                            )
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(30.dp).fillMaxHeight()
                        ) {
                            Text(
                                text = "Clientes potenciales (Leads)",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Image(
                            modifier = Modifier.size(100.dp).align(Alignment.BottomEnd),
                            painter = painterResource(Res.drawable.customer_person),
                            contentDescription = null
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().align(Alignment.BottomCenter).padding(horizontal = 32.dp).clickable {
                                onAction(LeadAction.OnSearchCustomerClick)
                            }
                    ) {
                        ElevatedCard(
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            elevation = CardDefaults.elevatedCardElevation(15.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Left side with the gradient and month
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(50.dp)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFFFFA726),  // Naranja claro
                                                    Color(0xFFFF5722)
                                                )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                                // Right side with the date and schedule
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Nombre",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color.Black.copy(
                                                alpha = 0.5f
                                            )
                                        )
                                    )
                                }

                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))

            }

            item {
                GenericContentLoading(
                    modifier = Modifier.height(300.dp),
                    data = state.productInteres,
                    retry = {
                        onAction(LeadAction.OnRefresh)
                    },
                    success = {
                        DashboardChart(
                            title = "Producto con mas interes",
                            orders = it,
                            products = provideProductsPropapel()
                        )
                    }
                )
            }

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
                    modifier = Modifier.height(300.dp),
                    data = state.customers,
                    retry = {
                        onAction(LeadAction.OnRefresh)
                    },
                    success = {
                        DonutChartInteractions(
                            Modifier,
                            it
                        )
                    }
                )
            }
            item {
                GenericContentLoading(
                    modifier = Modifier.height(270.dp),
                    data = state.customers,
                    retry = {
                        onAction(LeadAction.OnRefresh)
                    },
                    success = {
                        PieChartLeadsStatus(
                            modifier = Modifier,
                            listCustomer = it,
                            size = 200.dp
                        )
                    }
                )
            }
            if (state.customers is State.Empty) {
                item {
                    Card(
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9))
                    ) {
                        Column(
                            modifier = Modifier.fillParentMaxWidth().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Leads",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF007BFF)
                            )
                            Image(
                                painter = painterResource(Res.drawable.img_no_data),
                                contentDescription = null,
                                modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
                            )
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                            Text(
                                "No tienes leads registrados",
                                style = MaterialTheme.typography.bodyMedium
                            )

                        }
                    }
                }
            } else if (state.customers is State.Success) {
                val pageSize = 5
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

                item {
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
                item {
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
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
                    tint = Color.White
                )
            },
            expanded = lazyColumState.isScrolled(),
            onClick = {
                onAction(LeadAction.OnAddLeadClick)
            },
            text = {
                Text(
                    text = "Agregar lead",
                    color = Color.White
                )
            }
        )
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
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

@Composable
fun LazyListState.isScrolled(): Boolean {
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

