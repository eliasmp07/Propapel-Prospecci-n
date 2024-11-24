@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.dashboard.components.mobile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PanToolAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.CalendarDatesCard
import org.propapel.prospeccion.core.presentation.designsystem.components.DashboardCard
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.core.presentation.designsystem.components.PieChartLeadsStatus
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.handleResultView
import org.propapel.prospeccion.gerentePanel.presentation.dashboard.getImageSucursal
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMAction
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMState
import org.propapel.prospeccion.root.presentation.dashboard.components.ShimmerEffectDashboard
import org.propapel.prospeccion.root.presentation.detailLead.components.ButtonItemDirectAccess
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.root.presentation.leads.LeadAction
import org.propapel.prospeccion.root.presentation.leads.State
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.empty_info
import prospeccion.composeapp.generated.resources.login_img
import prospeccion.composeapp.generated.resources.logo
import prospeccion.composeapp.generated.resources.mid_reference
import prospeccion.composeapp.generated.resources.no_internet
import prospeccion.composeapp.generated.resources.person_with_phone

@Composable
fun DashboardScreenMobile(
    user: AuthInfo,
    state: DashboardSMState,
    onAction: (DashboardSMAction) -> Unit
) {

    val pullRefreshState =
        rememberPullRefreshState(
            state.isRefreshing,
            { onAction(DashboardSMAction.OnRefresh) })



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
        LazyColumn(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier.height(220.dp).fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(170.dp).shadow(
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
                        Image(
                            modifier = Modifier.fillMaxWidth().height(170.dp),
                            painter = painterResource(getImageSucursal(user) ?: Res.drawable.mid_reference),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            PrimaryYellowLight,
                                            Color.Black.copy(alpha = 0.3f)
                                        ),
                                        startY = 0f,
                                        endY = 100f
                                    ),
                                    shape = RoundedCornerShape(
                                        bottomEnd = 30.dp,
                                        bottomStart = 30.dp
                                    )
                                )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(100.dp),
                                painter = painterResource(Res.drawable.logo),
                                contentDescription = null
                            )
                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                            Column {
                                Text(
                                    text = "Bienvenido",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "${user.name} ${user.lastname}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = if (user.sucursales.isEmpty()) "" else user.sucursales.first(),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                            if (user.image.isNotBlank()) {
                                AsyncImage(
                                    model = user.image,
                                    contentDescription = "Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(
                                            90.dp
                                        )
                                        .clip(CircleShape)
                                        .pointerHoverIcon(PointerIcon.Hand)
                                        .border(
                                            border = BorderStroke(
                                                2.dp,
                                                Color.White
                                            ),
                                            shape = CircleShape
                                        )
                                )
                            } else {
                                Box(
                                    modifier = Modifier.size(90.dp).clip(CircleShape).background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = "Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(
                                                70.dp
                                            )
                                            .clip(CircleShape)
                                            .pointerHoverIcon(PointerIcon.Hand)
                                            .border(
                                                border = BorderStroke(
                                                    2.dp,
                                                    Color.White
                                                ),
                                                shape = CircleShape
                                            )
                                    )
                                }
                            }
                        }

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().align(Alignment.BottomCenter).padding(horizontal = 32.dp).clickable {

                            }
                    ) {
                        ElevatedCard(
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            elevation = CardDefaults.elevatedCardElevation(15.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ButtonItemDirectAccess(
                                    modifier = Modifier.padding(8.dp).weight(1f),
                                    text = "Crear Lead",
                                    icon = Icons.Filled.Person,
                                    colors = listOf(
                                        Color(0xFFFF6363),
                                        Color(0xFFAB47BC)
                                    ),
                                    onClick = {
                                        onAction(DashboardSMAction.OnMoveLeadScreenClick)
                                    }
                                )
                                ButtonItemDirectAccess(
                                    modifier = Modifier.padding(8.dp).weight(1f),
                                    text = "Crear cita",
                                    icon = Icons.Filled.CalendarMonth,
                                    colors = listOf(
                                        Color(0xFFFF6363),
                                        Color(0xFFAB47BC)
                                    ),
                                    onClick = {
                                        onAction(DashboardSMAction.OnCreateReminderClick)
                                    }
                                )
                                ButtonItemDirectAccess(
                                    modifier = Modifier.padding(8.dp).weight(1f),
                                    text = "Buscar Lead",
                                    icon = Icons.Filled.Search,
                                    colors = listOf(
                                        Color(0xFFFF6363),
                                        Color(0xFFAB47BC)
                                    ),
                                    onClick = {
                                        onAction(DashboardSMAction.OnSearchLead)
                                    }
                                )
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BannerPager(
                        items = listOf(
                            Pair(
                                null
                            ) {
                                GoalCard(
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
            }
            item {
                GenericContentLoading(
                    modifier = Modifier.height(
                        530.dp
                    ),
                    data = state.myCustomer,
                    retry = {
                        onAction(DashboardSMAction.OnRefresh)
                    },
                    success = {
                        PieChartLeadsStatus(
                            size = 270.dp,
                            listCustomer = it
                        )
                    }
                )
            }
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 8.dp,
                                horizontal = 16.dp
                            ),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (state.myCustomer is State.Success) {
                            DashboardCard(
                                title = "Clientes totales",
                                value = state.myCustomer.value.size.toString(),
                                percentage = "",
                                modifier = Modifier.weight(1f),
                                icon = Icons.Outlined.Groups,
                                background = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp
                        ),
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
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }


}