@file:OptIn(ExperimentalMaterial3Api::class,
            ExperimentalMaterial3WindowSizeClassApi::class
)

package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.CustomTopAppBar
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteAction
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date
import prospeccion.composeapp.generated.resources.create_project_img
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.project_confirm

@Composable
fun DashboardGerenteDesktop(
    state: DashboardGerenteState,
    onAction: (DashboardGerenteAction) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        ),
    ) {
        item {
            CustomTopAppBar(
                windowSizeClass = calculateWindowSizeClass(),
                user = state.user,
                onRefresh = {

                },
                reminders = listOf(),
                onAddLead = {
                    onAction(DashboardGerenteAction.OnAddLead)
                },
                profileImage = state.user.image,
                editProfile = {
                    onAction(DashboardGerenteAction.OnEditProfile)
                },
                onDarkTheme = {

                },
                totalNotifications = 10,
                onLogout = {
                    onAction(DashboardGerenteAction.OnLogout)
                },
                onSearch = {
                    onAction(DashboardGerenteAction.OnSearchLead)
                }
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GenericContentLoading(
                    modifier = Modifier.weight(1f).height(320.dp),
                    data = state.sucursal,
                    success = {
                        CardSucursalDesktop(
                            modifier = Modifier.weight(1f),
                            sucursalId = it.id,
                            leads = state.leadsBySucursal,
                            projects = state.projectsBySucursal,
                            sucursale = it
                        )
                    },
                    retry = {

                    }
                )

                GenericContentLoading(
                    modifier = Modifier.height(150.dp).weight(0.3f),
                    data = state.customersUser,
                    success = {
                        val dates = it.flatMap {
                            it.reminderUsers
                        }
                        CardInfoDesktop(
                            title = "Citas",
                            value = dates.size,
                            imageTypeCard = Res.drawable.calendar_date
                        )
                    },
                    retry = {

                    }
                )
                GenericContentLoading(
                    modifier = Modifier.height(150.dp).weight(0.3f),
                    data = state.customersUser,
                    success = {
                        val projects = it.flatMap {
                            it.projects
                        }
                        CardInfoDesktop(
                            title = "Proyectos",
                            value = projects.size,
                            imageTypeCard = Res.drawable.project_confirm
                        )
                    },
                    retry = {

                    }
                )
                GenericContentLoading(
                    modifier = Modifier.height(150.dp).weight(0.3f),
                    data = state.customersUser,
                    success = {
                        CardInfoDesktop(
                            title = "Clientes",
                            value = it.size,
                            imageTypeCard = Res.drawable.customer_person
                        )
                    },
                    retry = {

                    }
                )
                GenericContentLoading(
                    modifier = Modifier.height(150.dp).weight(0.3f),
                    data = state.customersUser,
                    success = {
                        val sales = it.flatMap {
                            it.projects
                        }.filter {
                            it.isCancel
                        }
                        CardInfoDesktop(
                            title = "Venta perdidas",
                            value = sales.size,
                            imageTypeCard = Res.drawable.calendar_date
                        )
                    },
                    retry = {

                    }
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GenericContentLoading(
                    modifier = Modifier.height(600.dp).weight(1f),
                    data = state.projectsCustomer,
                    success = {
                        LeadSixMothProjects(
                            modifier = Modifier.height(500.dp),
                            it
                        )
                    },
                    retry = {

                    }
                )
                GenericContentLoading(
                    modifier = Modifier.height(600.dp).weight(1f),
                    data = state.customersUser,
                    success = {
                        LeadAddThisMonth(
                            modifier = Modifier.height(600.dp),
                            customers = it
                        )
                    },
                    retry = {

                    }
                )
                PagerItemEjecutivoDeVentasDesktop(
                    modifierHorizontPage = Modifier.weight(1f).padding(end = 16.dp),
                    modifier = Modifier.padding(vertical = 16.dp).padding(bottom = 16.dp),
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

data class ItemsContent(
    val content: @Composable () -> Unit
)

@Composable
fun provideItemsContents(
    state: DashboardGerenteState
): List<ItemsContent> {
    return listOf(
        ItemsContent {

        }
    )
}