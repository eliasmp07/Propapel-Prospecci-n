package org.propapel.prospeccion.root.presentation.searchLead.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.presentation.leads.components.mobile.ItemLead
import org.propapel.prospeccion.root.presentation.searchLead.SearchLeadSMAction


internal val tabList = listOf("Nuevos", "En desarrollo", "En recuperación")

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ResultSearchScreen(
    modifier: Modifier,
    customers: List<Customer>,
    onClickCustomer: (String) -> Unit,
) {

    var shouldScrollToFirstPage by remember {
        mutableStateOf(false)
    }

    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        // provide pageCount
        tabList.size
    }
    val coroutineScope = rememberCoroutineScope()

    ScrollableTabRow(
        modifier = Modifier.fillMaxWidth(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        edgePadding = 8.dp,
        containerColor = PrimaryYellowLight,
        // selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier

    ) {
        // Add tabs for all of our pages
        tabList.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }

    HorizontalPager(
        modifier = Modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown(pass = PointerEventPass.Initial)
                    shouldScrollToFirstPage = false

                    val firstTouchX = down.position.x

                    do {
                        val event: PointerEvent = awaitPointerEvent(
                            pass = PointerEventPass.Initial
                        )

                        event.changes.forEach {

                            val diff = firstTouchX - it.position.x
                            val posX = it.position.x

                            val valid = pagerState.currentPage == tabList.lastIndex &&
                                    pagerState.settledPage == pagerState.currentPage &&
                                    (diff > size.width * .2f ||
                                            (it.position.x > 0 && it.position.x < size.width * .2f)) &&
                                    shouldScrollToFirstPage.not()

                            println(
                                "Diff $diff, posX: $posX , " +
                                        "current page: ${pagerState.currentPage}, " +
                                        "valid: $valid"
                            )

                            if (valid) {
                                coroutineScope.launch {
                                    println("🔥 Scrolling...")
                                    shouldScrollToFirstPage = true
                                    delay(50)
                                    pagerState.animateScrollToPage(
                                        0,
                                        animationSpec = tween(500)
                                    )
                                    shouldScrollToFirstPage = false
                                }
                            }
                        }

                    } while (event.changes.any { it.pressed })
                }
            },
        pageSpacing = 0.dp,
        beyondViewportPageCount = 1,
        userScrollEnabled = shouldScrollToFirstPage.not(),
        pageSize = PageSize.Fill,
        state = pagerState
    ) { page: Int ->
        when (page) {
            0 -> {
                CustomerListContent(
                    modifier = Modifier,
                    onClickCustomer = {
                        onClickCustomer(it)
                    },
                    customers = customers.filter {
                        it.typeClient == TypeOfClient.NUEVO.name
                    }
                )
            }
            1 -> {
                CustomerListContent(
                    modifier = Modifier,
                    onClickCustomer = {
                        onClickCustomer(it)
                    },
                    customers = customers.filter {
                        it.typeClient == TypeOfClient.DESARROLLO.name
                    }
                )
            }
            2 -> {
                CustomerListContent(
                    modifier = Modifier,
                    onClickCustomer = {
                        onClickCustomer(it)
                    },
                    customers = customers.filter {
                        it.typeClient == TypeOfClient.RECUPERACIÓN.name
                    }
                )
            }
        }
    }
}


@Composable
fun CustomerListContent(
    modifier: Modifier = Modifier,
    customers: List<Customer>,
    onClickCustomer: (String) -> Unit
){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            customers,
            key = { it.idCustomer }) {
            ItemLead(
                customer = it,
                onClick = {
                    onClickCustomer(it)
                }
            )
        }
    }
}