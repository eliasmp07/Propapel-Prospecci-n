@file:OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    KoinExperimentalAPI::class
)

package org.propapel.prospeccion.root.presentation.homeRoot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlack
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiWhite
import org.propapel.prospeccion.core.presentation.designsystem.components.CustomTopAppBar
import org.propapel.prospeccion.navigation.SidebarMenu
import org.propapel.prospeccion.navigation.utils.NavigationItem
import org.propapel.prospeccion.root.presentation.account.AccountSMAction
import org.propapel.prospeccion.root.presentation.account.AccountSMViewModel
import org.propapel.prospeccion.root.presentation.account.AccountScreenRoot
import org.propapel.prospeccion.root.presentation.dashboard.DashBoardScreenRoot
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMViewModel
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import org.propapel.prospeccion.root.presentation.dates.DateScreenRoot
import org.propapel.prospeccion.root.presentation.dates.DatesSMViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadSMViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadScreenRoot

@Composable
fun HomeScreen(
    viewModel: HomeRootViewModel,
    onDarkTheme: () -> Unit,
    onAddLead: () -> Unit,
    onUpdateLead: (String) -> Unit,
    onDetailLead: (String) -> Unit,
    onLogout: () -> Unit,
    onWebViewClick : (String) -> Unit,
    onSearchLead: () -> Unit,
    onDetailReminderCustomer: (String) -> Unit,
    onCreateReminder: () -> Unit,
    onUpdateProfile: () -> Unit,
    items : List<NavigationItem> = provideItemNavigationHomeScreenMobile()
) {


    val state by viewModel.state.collectAsState()

    val windowClass = calculateWindowSizeClass()

    if (windowClass.isMobile){
    val topAppBarState = rememberTopAppBarState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    val pagerState = rememberPagerState(pageCount = { items.size })

    var selectedItemIndex by remember { mutableStateOf(0) }

    val corrutine = rememberCoroutineScope()

    val viewModelAccount = koinViewModel<AccountSMViewModel>()
    val leadVieModel = koinViewModel<LeadSMViewModel>()
    val dashboardSMViewModel = koinViewModel<DashboardSMViewModel>()
    val datesViewModel = koinViewModel<DatesSMViewModel>()


    LaunchedEffect(pagerState.currentPage) {
        selectedItemIndex = pagerState.currentPage
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                user = state.user,
                reminders = state.reminders,
                profileImage = state.user.image,
                totalNotifications = state.reminders.size,
                scrollBehavior = scrollBehavior,
                onLogout = {
                    viewModel.onAction(HomeRootAction.OnLogoutClick)
                    onLogout()
                },
                editProfile = { onUpdateProfile() },
                windowSizeClass = windowClass,
                onDarkTheme = onDarkTheme,
                onSearch = {
                    onSearchLead()
                },
                isProfile = selectedItemIndex == items.lastIndex || selectedItemIndex == 0
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider()
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.ime),
                    containerColor = Color(0xFF007BFF),
                    tonalElevation = 8.dp
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = SoporteSaiWhite,
                                selectedTextColor = SoporteSaiWhite,
                                indicatorColor = Color.Transparent,
                                unselectedTextColor = SoporteSaiBlack,
                                unselectedIconColor = SoporteSaiBlack
                            ),
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                corrutine.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                if (selectedItemIndex == index) {
                                    Text(item.title, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) { page ->
            when (page) {
                0 -> {
                    DashBoardScreenRoot(
                        dashboardSMViewModel,
                        user = state.user,
                        windowSizeClass = windowClass,
                        onDetailReminderCustomer = {
                            onDetailReminderCustomer(it)
                        },
                        onSearchLead = onSearchLead,
                        onMoveLeadScreen = {
                            onAddLead()
                        },
                        onCreateReminder = {
                            onCreateReminder()
                        },
                        onWebView = {
                            onWebViewClick(it)
                        }
                    )
                }

                1 -> {
                    DateScreenRoot(
                        viewModel = datesViewModel,
                        windowWidthSizeClass = windowClass
                    )  // Pantalla de Citas
                }
                2 -> {
                    LeadScreenRoot(
                        viewModel = leadVieModel,
                        windowSizeClass = windowClass,
                        onDetailLead = onDetailLead,
                        onAddLead = onAddLead,
                        onUpdateLead = onUpdateLead,
                        onCreaReminder = onCreateReminder,
                        onSearchLead = onSearchLead
                    )  // Pantalla de Leads
                }

                3 -> {
                    AccountScreenRoot(
                        viewModelAccount,
                        windowSizeClass = windowClass,
                        onAction = { action ->
                            when (action) {
                                AccountSMAction.EditProfileClick -> onUpdateProfile()
                                AccountSMAction.OnLogoutClick -> onLogout()
                                else -> {}
                            }
                            viewModelAccount.onAction(action)
                        })
                }
            }
        }
    }}
    else{
        HomeScreenSalesDesktop(
            onWebViewClick = onWebViewClick,
            state = state,
            onUpdateProfile = onUpdateProfile,
            onCreateReminder = onCreateReminder,
            viewModel = viewModel,
            items = items,
            onUpdateLead = onUpdateLead,
            onSearchLead = onSearchLead,
            onDetailLead = onDetailLead,
            onAddLead = onAddLead,
            onDarkTheme = onDarkTheme,
            onLogout = onLogout,
            onDetailReminderCustomer = onDetailReminderCustomer,
            windowClass = windowClass
        )
    }
}

private fun provideItemNavigationHomeScreenMobile(): List<NavigationItem>{
    return listOf(
        NavigationItem(
            "Dashboard",
            Icons.Outlined.Dashboard,
            Icons.Default.Dashboard,
            false
        ),
        NavigationItem(
            "Citas",
            Icons.Outlined.DateRange,
            Icons.Default.DateRange,
            true,
            0
        ),
        NavigationItem(
            "Leads",
            Icons.Outlined.MyLocation,
            Icons.Default.MyLocation,
            false
        ),
        NavigationItem(
            "Perfil",
            Icons.Outlined.Person,
            Icons.Default.Person,
            false
        )
    )
}