@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.dashboard.components.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.CalendarDatesCard
import org.propapel.prospeccion.core.presentation.designsystem.components.DashboardCard
import org.propapel.prospeccion.core.presentation.designsystem.components.PieChartLeadsStatus
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMAction
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMState
import org.propapel.prospeccion.root.presentation.dashboard.components.ShimmerEffectDashboard
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.working_desk

@Composable
fun DashboardScreenMobile(
    user: AuthInfo,
    state: DashboardSMState,
    onAction: (DashboardSMAction) -> Unit
) {

    val pullRefreshState =
        rememberPullRefreshState(state.isRefreshing, { onAction(DashboardSMAction.OnRefresh) })

    Box(
        modifier = Modifier.fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        if (state.isLoading) {
            ShimmerEffectDashboard()
        } else {
            LazyColumn(
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
                                text = "Bienvenido de nuevo",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF007BFF)
                            )
                            Text(
                                "${user.name} ${user.lastname}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BannerPager(state = listOf(Banner(), Banner()))
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(150.dp).clip(RoundedCornerShape(12.dp)).clickable {
                                onAction(DashboardSMAction.OnMoveLeadScreenClick)
                            },
                        ) {
                            ElevatedCard(
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                Column(
                                    modifier = Modifier.size(100.dp).background(MaterialTheme.colorScheme.primary),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Clientes",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            shadow = Shadow(
                                                offset = Offset(10f, 10f),
                                                blurRadius = 10f
                                            )
                                        ),
                                        color = Color.White
                                    )
                                }
                            }
                            Image(
                                painter = painterResource(Res.drawable.working_desk),
                                modifier = Modifier.size(80.dp).align(Alignment.BottomEnd),
                                contentDescription = null
                            )
                        }
                    }
                }
                item {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            PieChartLeadsStatus(
                                size = 300.dp,
                                listCustomer = state.customers
                            )
                        }
                    }
                }
                item {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (state.customers.isNotEmpty()) {
                                DashboardCard(
                                    title = "Clientes totales",
                                    value = state.customers.size.toString(),
                                    percentage = "",
                                    modifier = Modifier.weight(1f),
                                    icon = Icons.Outlined.Groups,
                                    background = MaterialTheme.colorScheme.primary
                                )
                            }

                            /*

                            DashboardCard(
                                title = "Clientes Nuevos",
                                value = state.customers.filter {
                                    it.typeClient == "Nuevo"
                                }.size.toString(),
                                percentage = "20",
                                modifier = Modifier.weight(1f),
                                icon = Icons.Outlined.Person,
                                background = Color(0XFF723bde)
                            )
                             */

                        }
                    }
                }
                item {
                    Column {
                        GoalCard(
                            title = "Mi objetivo",
                            currentValue = state.totalRemindersMoth.toFloat(),
                            goalValue = 10f,
                            icon = Icons.Filled.Flag,
                            background = SuccessGreen
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CalendarDatesCard(
                            state = state,
                            modifier = Modifier.weight(1f),
                            onAction = onAction
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }


}