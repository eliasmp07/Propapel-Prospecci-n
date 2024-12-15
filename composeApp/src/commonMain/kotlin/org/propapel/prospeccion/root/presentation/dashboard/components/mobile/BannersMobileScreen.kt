package org.propapel.prospeccion.root.presentation.dashboard.components.mobile


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Banner(
    val description: String = "",
    val id: Int = 0,
    val url: String = "",
    val imageUrl: String = "",
    val type: String = ""
)

@Composable
fun BannerPaggerWindows(
    modifier: Modifier = Modifier,
    onClickBanner: (String) -> Unit = {},
    items: List<Pair<Banner?, @Composable () -> Unit>> // Permite que el Banner sea nulo
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { items.size })

    // Auto-desplazamiento del pager
    LaunchedEffect(pagerState) {
        while (true) {
            delay(10000) // Cambia de página cada minuto
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % items.size)
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = modifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().height(500.dp)
            ) { page ->
                val (banner, content) = items[page]
                if (banner != null) {
                    // Si hay un banner, mostramos el BannerItemMobileScreen
                    BannerItemDesktopScreen(
                        modifier = Modifier.fillMaxSize(),
                        banner = banner,
                        onClickBanner = {
                            onClickBanner(it)
                        }
                    )
                } else {
                    // Si no hay banner, mostramos el composable correspondiente
                    content()
                }
            }

            // Indicadores de página
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(
                        bottom = 4.dp,
                        top = 4.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.White
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(15.dp)
                    )
                }
            }
        }
        IconButton(
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White
            ),
            modifier = Modifier.align(Alignment.CenterStart).clip(CircleShape).pointerHoverIcon(PointerIcon.Hand)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Previous"
            )
        }
        IconButton(
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White
            ),
            modifier = Modifier.align(Alignment.CenterEnd).clip(CircleShape).pointerHoverIcon(PointerIcon.Hand)
        ) {
            Icon(
                modifier = Modifier.rotate(180f),
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Next"
            )
        }
    }
}

@Composable
fun BannerPager(
    modifier: Modifier = Modifier,
    items: List<Pair<Banner?, @Composable () -> Unit>>,// Permite que el Banner sea nulo
    onClickBanner: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { items.size })

    // Auto-desplazamiento del pager
    LaunchedEffect(pagerState) {
        while (true) {
            delay(10000) // Cambia de página cada minuto
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % items.size)
        }
    }

    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val (banner, content) = items[page]
            if (banner != null) {
                // Si hay un banner, mostramos el BannerItemMobileScreen
                BannerItemMobileScreen(banner = banner, onClickBanner = {
                    onClickBanner(it)
                })
            } else {
                // Si no hay banner, mostramos el composable correspondiente
                content()
            }
        }

        // Indicadores de página
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(
                    bottom = 4.dp,
                    top = 4.dp
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.White
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }

}
