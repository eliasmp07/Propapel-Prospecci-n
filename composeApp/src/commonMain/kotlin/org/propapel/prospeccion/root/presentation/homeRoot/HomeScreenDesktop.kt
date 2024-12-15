@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class,
            KoinExperimentalAPI::class,
            ExperimentalMaterial3Api::class
)

package org.propapel.prospeccion.root.presentation.homeRoot

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.Navigation
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.propapel.prospeccion.core.presentation.designsystem.components.CustomTopAppBar
import org.propapel.prospeccion.navigation.SidebarMenu
import org.propapel.prospeccion.navigation.utils.NavigationItem
import org.propapel.prospeccion.root.presentation.account.AccountSMAction
import org.propapel.prospeccion.root.presentation.account.AccountSMViewModel
import org.propapel.prospeccion.root.presentation.account.AccountScreenRoot
import org.propapel.prospeccion.root.presentation.account.components.ImageProfile
import org.propapel.prospeccion.root.presentation.dashboard.DashBoardScreenRoot
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMViewModel
import org.propapel.prospeccion.root.presentation.dates.DateScreenRoot
import org.propapel.prospeccion.root.presentation.dates.DatesSMViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadSMViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadScreenRoot

@Composable
fun HomeScreenSalesDesktop(
    viewModel: HomeRootViewModel,
    onDarkTheme: () -> Unit,
    onAddLead: () -> Unit,
    onUpdateLead: (String) -> Unit,
    onDetailLead: (String) -> Unit,
    state: HomeSMRootState,
    onLogout: () -> Unit,
    onWebViewClick : (String) -> Unit,
    onSearchLead: () -> Unit,
    onDetailReminderCustomer: (String) -> Unit,
    onCreateReminder: () -> Unit,
    onUpdateProfile: () -> Unit,
    windowClass: WindowSizeClass = calculateWindowSizeClass(),
    items: List<NavigationItem> = provideScreenHome()
){
    val corrutine = rememberCoroutineScope()
    var selectedItemIndex by remember { mutableStateOf(0) }
    val viewModelAccount = koinViewModel<AccountSMViewModel>()
    val leadVieModel = koinViewModel<LeadSMViewModel>()
    val dashboardSMViewModel = koinViewModel<DashboardSMViewModel>()
    val datesViewModel = koinViewModel<DatesSMViewModel>()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
            ) {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally).size(200.dp).clip(CircleShape)
                ){
                    if (
                        state.user.image.isEmpty()
                    ){
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
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
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.background),
                                contentScale = ContentScale.Crop,
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        }
                    }else{
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = state.user.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = Color.White,
                            selectedIconColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp).pointerHoverIcon(PointerIcon.Hand),
                        icon = {
                            Icon(
                                imageVector = if(index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            corrutine.launch {
                                drawerState.close()
                            }
                            selectedItemIndex = index
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        selected = selectedItemIndex == index
                    )
                }
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                NavigationDrawerItem(
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        corrutine.launch {
                            drawerState.close()
                        }
                        onLogout()
                    },
                    label = {
                        Text(
                            text = "Cerrar sesion",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    selected = false
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        },
    ) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    windowSizeClass = windowClass,
                    onLogout = onLogout,
                    onAddLead = onAddLead,
                    onMenu = {
                        corrutine.launch {
                            if (drawerState.isOpen){
                                drawerState.close()
                            }else{
                                drawerState.open()
                            }
                        }

                    },
                    onDarkTheme = onDarkTheme,
                    reminders = state.reminders,
                    user = state.user,
                    onSearch = onSearchLead,
                    editProfile = onUpdateProfile,
                    totalNotifications = state.reminders.size
                )
            }
        ){ innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 0.dp)
                ) {
                    when (selectedItemIndex) {
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
            }

        }
    }


}


private fun provideScreenHome(): List<NavigationItem>{
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