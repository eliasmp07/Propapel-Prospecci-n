@file:OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3Api::class,
    KoinExperimentalAPI::class
)

package org.propapel.prospeccion.selectSucursal.presentation.root

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.propapel.prospeccion.core.presentation.designsystem.components.CustomTopAppBar
import org.propapel.prospeccion.navigation.utils.NavigationItem
import org.propapel.prospeccion.root.presentation.account.AccountSMAction
import org.propapel.prospeccion.root.presentation.account.AccountSMViewModel
import org.propapel.prospeccion.root.presentation.account.AccountScreenRoot
import org.propapel.prospeccion.root.presentation.dates.DateScreenRoot
import org.propapel.prospeccion.root.presentation.dates.DatesSMViewModel
import org.propapel.prospeccion.root.presentation.homeRoot.HomeRootViewModel
import org.propapel.prospeccion.root.presentation.homeRoot.toggle
import org.propapel.prospeccion.root.presentation.leads.LeadSMViewModel
import org.propapel.prospeccion.root.presentation.leads.LeadScreenRoot
import org.propapel.prospeccion.root.presentation.users.UserSMViewModel
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

    val items = provideGerentePanelRoot()

    var selectedItemIndex by remember { mutableStateOf(0) }

    val corrutine = rememberCoroutineScope()

    val viewModelAccount = koinViewModel<AccountSMViewModel>()
    val leadVieModel = koinViewModel<LeadSMViewModel>()
    val userSMViewModel = koinViewModel<UserSMViewModel>()
    val datesViewModel = koinViewModel<DatesSMViewModel>()


    val dashboardGerenteViewModel = koinViewModel<DashboardGerenteViewModel>()


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
                            modifier = Modifier.fillMaxSize().border(
                                BorderStroke(
                                    2.dp,
                                    MaterialTheme.colorScheme.onBackground
                                ),
                                CircleShape
                            ),
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
                            imageVector = Icons.AutoMirrored.Filled.Logout,
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
                            drawerState.toggle()
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
                when (selectedItemIndex) {
                    0 -> {
                        LaunchedEffect(Unit){
                            dashboardGerenteViewModel.getUserBySucursal(state.sucursalId)
                            dashboardGerenteViewModel.getSucursal(state.sucursalId)
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
                            onDetailReminderCustomer = {
                                onDetailReminderCustomer(it)
                            },
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
                                    AccountSMAction.OnSelectSucursal -> onSelectSucursal()
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

@Composable
fun provideGerentePanelRoot(): List<NavigationItem>{
    return listOf(
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
    )
}

