@file:OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package org.propapel.prospeccion.root.presentation.homeRoot

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
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
import org.propapel.prospeccion.root.presentation.dates.DateScreenRoot
import org.propapel.prospeccion.root.presentation.leads.LeadSMViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadScreenRoot

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.remember
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiGray40
import org.propapel.prospeccion.root.presentation.users.UserSMViewModel
import org.propapel.prospeccion.root.presentation.users.UsersSMScreenRoot
import kotlin.math.abs

@Composable
fun HomeScreen(
    viewModel: HomeRootViewModel,
    onDarkTheme: () -> Unit,
    onAddLead: () -> Unit,
    onLogout: () -> Unit,
    onDetailReminderCustomer: (String) -> Unit,
    onUpdateProfile: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val topAppBarState = rememberTopAppBarState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    val (items, isAdmin) = if (state.user.isAdmin) {
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
                    "Users",
                    Icons.Outlined.Groups,
                    Icons.Default.Groups,
                    false
                ),
                NavigationItem(
                    "Leads",
                    Icons.Outlined.StackedLineChart,
                    Icons.Default.StackedLineChart,
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
                    Icons.Outlined.StackedLineChart,
                    Icons.Default.StackedLineChart,
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

    val windowClass = calculateWindowSizeClass()
    val showNavigationRail = windowClass.widthSizeClass != WindowWidthSizeClass.Compact
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    var previousItemIndex by rememberSaveable { mutableStateOf(0) }

    val viewModelAccount = koinViewModel<AccountSMViewModel>()
    val leadVieModel = koinViewModel<LeadSMViewModel>()
    val dashboardSMViewModel = koinViewModel<DashboardSMViewModel>()
    val userSMViewModel = koinViewModel<UserSMViewModel>()

    val swipeThreshold = 100f // Umbral mínimo de desplazamiento
    var totalDragAmount by remember { mutableStateOf(0f) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                user = state.user,
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
                isProfile = selectedItemIndex == items.lastIndex
            )
        },
        bottomBar = {
            if (!showNavigationRail) {
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
                                previousItemIndex = selectedItemIndex
                                selectedItemIndex = index
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
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (abs(totalDragAmount) > swipeThreshold) {
                                if (totalDragAmount > 0) {
                                    // Swipe right
                                    if (selectedItemIndex > 0) {
                                        previousItemIndex = selectedItemIndex
                                        selectedItemIndex -= 1
                                    }
                                } else {
                                    // Swipe left
                                    if (selectedItemIndex < items.size - 1) {
                                        previousItemIndex = selectedItemIndex
                                        selectedItemIndex += 1
                                    }
                                }
                            }
                            totalDragAmount = 0f
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            totalDragAmount += dragAmount
                        }
                    )
                }
        ) {
            if (showNavigationRail) {
                SidebarMenu(
                    items = items,
                    selectedItemIndex = selectedItemIndex,
                    onMenuItemClick = { index -> selectedItemIndex = index },
                    initialExpandedState = false
                )
            }

            // Animación más lenta para las transiciones
            AnimatedContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding()),
                transitionSpec = {
                    if (selectedItemIndex > previousItemIndex) {
                        slideInHorizontally(
                            animationSpec = tween(300),
                            initialOffsetX = { fullWidth -> fullWidth }) togetherWith
                                slideOutHorizontally(
                                    animationSpec = tween(300),
                                    targetOffsetX = { fullWidth -> -fullWidth })
                    } else {
                        slideInHorizontally(
                            animationSpec = tween(300),
                            initialOffsetX = { fullWidth -> -fullWidth }) togetherWith
                                slideOutHorizontally(
                                    animationSpec = tween(300),
                                    targetOffsetX = { fullWidth -> fullWidth })
                    }
                },
                targetState = selectedItemIndex
            ) { stateScreen ->
                if (state.user.isAdmin) {
                    when (stateScreen) {
                        0 -> {
                            DashBoardScreenRoot(
                                dashboardSMViewModel,
                                onDetailReminderCustomer = { onDetailReminderCustomer(it) },
                                user = state.user,
                                onMoveLeadScreen = {
                                    previousItemIndex = selectedItemIndex
                                    selectedItemIndex = 3
                                },
                               windowSizeClass =  windowClass,
                            )
                        }

                        1 -> {
                            DateScreenRoot()  // Pantalla de Citas
                        }

                        2 -> {
                            UsersSMScreenRoot(
                                viewModel = userSMViewModel,
                                onAddLead
                            )  // Pantalla de Usuarios
                        }

                        3 -> {
                            LeadScreenRoot(
                                leadVieModel,
                                windowClass,
                                onAddLead
                            )  // Pantalla de Leads
                        }

                        4 -> {
                            AccountScreenRoot(
                                viewModelAccount,
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
                } else {
                    when (stateScreen) {
                        0 -> {
                            DashBoardScreenRoot(
                                dashboardSMViewModel,
                                user = state.user,
                                windowSizeClass = windowClass,
                                onDetailReminderCustomer = {
                                    onDetailReminderCustomer(it)
                                },
                                onMoveLeadScreen = {

                                }
                            )
                        }

                        1 -> {
                            DateScreenRoot()  // Pantalla de Citas
                        }

                        2 -> {
                            LeadScreenRoot(
                                leadVieModel,
                                windowClass,
                                onAddLead
                            )  // Pantalla de Leads
                        }

                        3 -> {
                            AccountScreenRoot(
                                viewModelAccount,
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
