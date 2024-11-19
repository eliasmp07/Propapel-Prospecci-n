package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.presentation.designsystem.components.util.carouselTransition
import org.propapel.prospeccion.core.presentation.ui.extensions.ZERO_INT
import org.propapel.prospeccion.core.presentation.ui.extensions.ifNotZero
import org.propapel.prospeccion.core.presentation.ui.extensions.ifTrue
import org.propapel.prospeccion.core.presentation.ui.extensions.tryOrDefault

@Composable
fun <T> CarouselBanners(
    modifier: Modifier = Modifier,
    list: List<T>,
    enableIndicator: Boolean = false,
    enableGoToNextItem: Boolean = false,
    itemSpacing: Dp = 16.dp,
    enableLoop: Boolean = false,
    widthItem: Dp = 300.dp,
    isCenterAlignment: Boolean = true,
    content: @Composable (contentModifier: Modifier, currentPage: Int, page: Int) -> Unit,
) {

    val listState = rememberPagerState(
        pageCount = { list.size },
        initialPage = 0,
    )
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        HorizontalPager(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->

            content(
                Modifier
                    .width(widthItem)
                    .height(112.dp)
                    .carouselTransition(page, listState),
                if (enableLoop) tryOrDefault(ZERO_INT) { listState.currentPage % list.size } else listState.currentPage,
                if (enableLoop) tryOrDefault(ZERO_INT) { page % list.size } else page,
            )
        }

        enableIndicator.ifTrue {
            Indicator(
                modifier = Modifier.align(Alignment.CenterEnd),
                listState = listState,
                coroutineScope = coroutineScope,
                enableGoToNextItem = enableGoToNextItem,
                lastIndex = list.size - 1
            )
        }
    }
}

@Composable
private fun Indicator(
    modifier: Modifier,
    listState: PagerState,
    coroutineScope: CoroutineScope,
    enableGoToNextItem: Boolean,
    lastIndex: Int,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = listState.canScrollForward,
        enter = scaleIn(),
        exit = scaleOut(),
    ) {
        RightArrowIcon(modifier = Modifier.padding(end = 4.dp)) {
            val nextIndex = if (enableGoToNextItem) listState.currentPage + 1 else lastIndex
            nextIndex.ifNotZero {
                coroutineScope.launch {
                    listState.animateScrollToPage(nextIndex)
                }
            }
        }
    }
}

@Composable
fun RightArrowIcon(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Icon(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = CircleShape
            )
            .background(
                color = Color.Gray,
                shape = CircleShape
            )
            .clickable { onClick() }
            .padding(8.dp)
            .size(20.dp),
        imageVector = Icons.Default.ArrowRight,
        contentDescription = null,
        tint = Color.Blue
    )
}