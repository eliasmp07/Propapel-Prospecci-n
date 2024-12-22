@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.selectSucursal.presentation.selectHome.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.selectSucursal.domain.model.Sucursale
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalAction
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalState
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.getImageSucursalSelected
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo
import prospeccion.composeapp.generated.resources.merida
import kotlin.math.absoluteValue

@Composable
fun SelectSucursalMobile(
    state: SelectSucursalState,
    onAction: (SelectSucursalAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido ${state.authInfo.name}, selecione una sucursal, para comenzar",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        Pager(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .offset(x = (-64).dp)
                .fillMaxWidth(),
            state = state,
            onAction = onAction
        )
    }
}

@Composable
fun LayeredCardDesign(
    modifier: Modifier,
    sucusales: Sucursale,
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
                    width = 160.dp,
                    height = 246.dp
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
                    width = 204.dp,
                    height = 289.dp
                )
                .background(
                    color = Color(0xFFFCBABA),
                    shape = RoundedCornerShape(16.dp)
                )
        )
        Box(
            modifier = Modifier
                .size(
                    width = 248.dp,
                    height = 334.dp
                )
                .background(
                    color = Color(0xFFFB7D8A),
                    shape = RoundedCornerShape(16.dp)
                ).clickable {
                    onSucursalSelected(sucusales.id)
                }
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = 14.dp,
                        top = 18.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(-10.dp)
            ) {

                Image(
                    modifier = Modifier.padding(16.dp).size(
                        height = 25.dp,
                        width = 40.dp
                    ),
                    painter = painterResource(Res.drawable.logo),
                    contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
                    contentDescription = null
                )
                Text(
                    "Sucursal",
                    textAlign = TextAlign.Center,
                    fontSize = 47.sp,
                    color = Color.White,
                )
                Text(
                    text = sucusales.nombre,
                    fontSize = 47.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White.copy(alpha = 0.5f),
                )
            }
            AsyncImage(
                modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp).clip(RoundedCornerShape(16.dp)),
                contentDescription =null,
                contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
                model = sucusales.image ?: Res.drawable.merida
            )
        }
    }
}

@Composable
fun Pager(
    modifier: Modifier = Modifier,
    state: SelectSucursalState,
    onAction: (SelectSucursalAction) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { state.sucusales.count() })

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 75.dp),
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
        LayeredCardDesign(
            modifier = modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            onSucursalSelected = {
                onAction(SelectSucursalAction.OnSucursalSelectedClick(it))
            },
            offsetY = offsetY,
            sucusales = state.sucusales[page]
        )
    }
}