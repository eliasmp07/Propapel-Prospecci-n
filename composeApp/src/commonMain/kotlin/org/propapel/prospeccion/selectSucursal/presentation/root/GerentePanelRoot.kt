@file:OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3Api::class,
    KoinExperimentalAPI::class
)

package org.propapel.prospeccion.selectSucursal.presentation.root

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
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Groups
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
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
import org.propapel.prospeccion.root.presentation.dates.DateScreenRoot
import org.propapel.prospeccion.root.presentation.dates.DatesSMViewModel
import org.propapel.prospeccion.root.presentation.homeRoot.HomeRootAction
import org.propapel.prospeccion.root.presentation.homeRoot.HomeRootViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadSMViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadScreenRoot
import org.propapel.prospeccion.root.presentation.users.UserSMViewModel
import org.propapel.prospeccion.root.presentation.users.UsersSMScreenRoot
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteScreenRoot
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteViewModel
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.ic_home
import prospeccion.composeapp.generated.resources.ic_home_outline

@Composable
fun GerentePanelScreen(
    viewModel: HomeRootViewModel,
    onDarkTheme: () -> Unit,
    onAddLead: () -> Unit,
    onUpdateLead: (String) -> Unit,
    onDetailLead: (String) -> Unit,
    onLogout: () -> Unit,
    onSelectSucursal: () -> Unit,
    onSearchLead: () -> Unit,
    onDetailReminderCustomer: (String) -> Unit,
    onCreateReminder: () -> Unit,
    onUpdateProfile: () -> Unit
) {

    val windowClass = calculateWindowSizeClass()

    val state by viewModel.state.collectAsState()

    val topAppBarState = rememberTopAppBarState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    val (items, isAdmin) = if (state.user.roles.isNotEmpty()) {
        Pair(
            listOf(
                NavigationItem(
                    "Dashboard",
                    vectorResource(Res.drawable.ic_home_outline),
                    vectorResource(Res.drawable.ic_home),
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
                    "Users",
                    Icons.Outlined.Groups,
                    Icons.Default.Groups,
                    false
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
            ),
            true
        )
    } else {
        Pair(
            listOf(
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
            ),
            false
        )
    }

    val pagerState = rememberPagerState(pageCount = { items.size })
    var selectedItemIndex by remember { mutableStateOf(0) }


    val showNavigationRail = windowClass.widthSizeClass != WindowWidthSizeClass.Compact
    val corrutine = rememberCoroutineScope()

    val viewModelAccount = koinViewModel<AccountSMViewModel>()
    val leadVieModel = koinViewModel<LeadSMViewModel>()
    val userSMViewModel = koinViewModel<UserSMViewModel>()
    val datesViewModel = koinViewModel<DatesSMViewModel>()


    val dashboardGerenteViewModel = koinViewModel<DashboardGerenteViewModel>()




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
                isProfile = selectedItemIndex == items.lastIndex
            )
        },
        bottomBar = {
            if (!showNavigationRail) {
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
                                        Text(item.title)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            if (showNavigationRail) {
                SidebarMenu(
                    items = items,
                    selectedItemIndex = selectedItemIndex,
                    onMenuItemClick = { index ->
                        selectedItemIndex = index
                        corrutine.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    initialExpandedState = false
                )
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding()),
            ) { page ->
                if (state.user.roles.contains("Gerente Regional")) {
                    when (page) {
                        0 -> {
                            LaunchedEffect(Unit){
                                dashboardGerenteViewModel.getUserBySucursal(state.sucursalId)
                            }
                            DashboardGerenteScreenRoot(
                                user = state.user,
                                sucursalId = state.sucursalId,
                                viewModel = dashboardGerenteViewModel
                            )
                        }

                        1 -> {
                            DateScreenRoot(
                                viewModel = datesViewModel,
                                windowWidthSizeClass = windowClass
                            )  // Pantalla de Citas
                        }
                        2 -> {
                            UsersSMScreenRoot(
                                viewModel = userSMViewModel,
                                sucusalId = state.sucursalId,
                                onAddLead
                            )  // Pantalla de Usuarios
                        }

                        3 -> {
                            LeadScreenRoot(
                                viewModel = leadVieModel,
                                onUpdateLead = onUpdateLead,
                                windowSizeClass = windowClass,
                                onDetailLead = onDetailLead,
                                onAddLead = onAddLead,
                                onCreaReminder = onCreateReminder,
                                onSearchLead = onSearchLead
                            )  // Pantalla de Leads
                        }
                        4 -> {
                            AccountScreenRoot(
                                viewModelAccount,
                                windowSizeClass = windowClass,
                                onAction = { action ->
                                    when (action) {
                                        AccountSMAction.EditProfileClick -> onUpdateProfile()
                                        AccountSMAction.OnLogoutClick -> onLogout()
                                        AccountSMAction.OnSelectSucursal -> onSelectSucursal()
                                        else -> {}
                                    }
                                    viewModelAccount.onAction(action)
                                })
                        }
                    }
                } else {
                    when (page) {
                        0 -> {
                            DashboardGerenteScreenRoot(
                                user = state.user,
                                sucursalId = state.sucursalId,
                                viewModel = dashboardGerenteViewModel
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
            }
        }
    }
}



