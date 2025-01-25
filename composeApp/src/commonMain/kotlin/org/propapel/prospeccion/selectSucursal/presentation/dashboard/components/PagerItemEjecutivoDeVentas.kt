package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowLeft
import androidx.compose.material.icons.automirrored.rounded.ArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.ArrowLeft
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.selectSucursal.domain.model.UserItem
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteAction
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteState
import kotlin.math.absoluteValue

@Composable
fun LayeredCardDesignEjecutivosDeVentas(
    modifier: Modifier,
    ejecutivo: UserItem,
    onSucursalSelected: (Int) -> Unit,
    offsetY: Float
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .offset(y = offsetY.dp)
                .size(
                    width = 140.dp,
                    height = 226.dp
                )
                .background(
                    color = Color(0xFFF9E0E2),
                    shape = RoundedCornerShape(16.dp)
                )
        )
        Box(
            modifier = Modifier
                .offset(y = (offsetY / 2).dp)
                .size(
                    width = 184.dp,
                    height = 269.dp
                )
                .background(
                    color = Color(0xFFFCBABA),
                    shape = RoundedCornerShape(16.dp)
                )
        )
        Box(
            modifier = Modifier
                .size(
                    width = 228.dp,
                    height = 314.dp
                )
                .background(
                    color = Color(0xFFFB7D8A),
                    shape = RoundedCornerShape(16.dp)
                ).clickable {
                    onSucursalSelected(ejecutivo.id)
                }.pointerHoverIcon(PointerIcon.Hand)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = 14.dp,
                        top = 18.dp
                    ),
            ) {
                Text(
                    "Ejecutivo",
                    fontSize = 30.sp,
                    color = Color.White,
                )
                Text(
                    ejecutivo.name,
                    fontSize = 25.sp,
                    color = Color.White.copy(alpha = 0.5f),
                )
                Text(
                    ejecutivo.puesto,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.5f),
                )
            }
            Column(
                modifier = Modifier.align(Alignment.BottomStart),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.padding(start = 13.dp)
                ) {
                    /*
                    Image(
                        painter = painterResource(R.drawable.drink_image),
                        contentDescription = ""
                    )

                     */
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        "Total de lead: ${ejecutivo.customers.size}",
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 13.dp),
                    verticalArrangement = Arrangement.spacedBy((-10).dp)
                ) {
                    val customerNew = ejecutivo.customers.filter {  it.typeOfClient == TypeOfClient.NUEVO.name || it.typeOfClient == TypeOfClient.NUEVO.description }
                    val customerDesarrollo =
                        ejecutivo.customers.filter {  it.typeOfClient == TypeOfClient.DESARROLLO.name || it.typeOfClient == TypeOfClient.DESARROLLO.description }
                    val customerRecuperacion =
                        ejecutivo.customers.filter {     it.typeOfClient == "RECUPERACIÓN" || it.typeOfClient == TypeOfClient.RECUPERACION.description }
                    Text(
                        "Nuevos: ${customerNew.size}",
                        fontSize = 12.sp,
                        color = Color.White,
                    )
                    Text(
                        "Desarrollo: ${customerDesarrollo.size}",
                        fontSize = 12.sp,
                        color = Color.White,
                    )
                    Text(
                        "Recuperacion: ${customerRecuperacion.size}",
                        fontSize = 12.sp,
                        color = Color.White,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 13.dp)
                ) {
                    //Image(painter = painterResource(R.drawable.time_image), contentDescription = "")
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = Color.White
                    )
                    val reminders = ejecutivo.customers.flatMap {
                        it.reminderUsers
                    }
                    Text(
                        "Total de citas: ${reminders.size}",
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

            }
            ImageEjecutivo(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(
                        x = 49.dp,
                        y = (5).dp
                    ),
                profileImg = ejecutivo.image,
                isAdmin = ejecutivo.puesto.contains("Administrador") || ejecutivo.puesto.contains("Gerente Regional")
            )
        }
    }
}

@Composable
fun ImageEjecutivo(
    modifier: Modifier = Modifier,
    profileImg: String,
    isAdmin: Boolean,
) {
    Box(modifier = modifier.size(130.dp).padding(10.dp)) {
        if (profileImg.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.onBackground
                        ),
                        CircleShape
                    ).background(Color.White)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = ContentScale.Crop,
                    model = profileImg,
                    contentDescription = null
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.onBackground
                        ),
                        CircleShape
                    ).background(Color.White)
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = ContentScale.Crop,
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
        }
        if (isAdmin) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.WorkspacePremium,
                    tint = Color.Yellow,
                    contentDescription = null,
                )
            }
        }

    }
}

@Composable
fun PagerItemEjecutivoDeVentas(
    modifier: Modifier = Modifier,
    state: DashboardGerenteState,
    onAction: (DashboardGerenteAction) -> Unit
) {
    GenericContentLoading(
        data = state.users,
        retry = {

        },
        success = {
            val pagerState = rememberPagerState(pageCount = { it.count() })

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(end = 150.dp),
            ) { page ->
                val pageOffset = (
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                        ).absoluteValue

                val offsetY = lerp(
                    start = 80f,
                    stop = 0f,
                    fraction = pageOffset.coerceIn(
                        0f,
                        1f
                    )
                )

                val scale = lerp(
                    start = 0.75f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(
                        0f,
                        1f
                    )
                )
                LayeredCardDesignEjecutivosDeVentas(
                    modifier = modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        },
                    onSucursalSelected = {

                    },
                    offsetY = offsetY,
                    ejecutivo = it[page]
                )
            }
        }
    )
}

@Composable
fun PagerItemEjecutivoDeVentasDesktop(
    modifierHorizontPage: Modifier = Modifier,
    modifier: Modifier = Modifier,
    state: DashboardGerenteState,
    onAction: (DashboardGerenteAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    GenericContentLoading(
        modifier = modifierHorizontPage,
        data = state.users,
        retry = {

        },
        success = {
            val pagerState = rememberPagerState(pageCount = { it.count() })
            Column {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Row(
                    modifier = Modifier.shadow(
                        elevation = 15.dp
                    ).fillMaxWidth().background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            scope.launch {
                                val previousPage = pagerState.currentPage - 1
                                if (previousPage >= 0) {
                                    pagerState.animateScrollToPage(previousPage)
                                }
                            }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowLeft,
                                contentDescription = null
                            )
                        }
                    )
                    Text(
                        text = "Ejecutivos de ventas",
                        style = MaterialTheme.typography.titleMedium
                    )
                    IconButton(
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            scope.launch {
                                val nextPage = pagerState.currentPage + 1
                                if (nextPage < pagerState.pageCount) {
                                    pagerState.animateScrollToPage((pagerState.currentPage + 1) % it.size)
                                }
                            }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowRight,
                                contentDescription = null
                            )
                        }
                    )
                }
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(end = 150.dp),
                ) { page ->
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                            ).absoluteValue

                    val offsetY = lerp(
                        start = 80f,
                        stop = 0f,
                        fraction = pageOffset.coerceIn(
                            0f,
                            1f
                        )
                    )

                    val scale = lerp(
                        start = 0.75f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(
                            0f,
                            1f
                        )
                    )
                    LayeredCardDesignEjecutivosDeVentas(
                        modifier = modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            },
                        onSucursalSelected = {

                        },
                        offsetY = offsetY,
                        ejecutivo = it[page]
                    )
                }
            }

        }
    )
}