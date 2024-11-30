package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteAction
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteState

@Composable
fun DashboardGerenteMobile(
    user: AuthInfo,
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
        )
    ) {
        item{
            TopBarOptions(
                user
            )
        }
        item {
            GenericContentLoading(
                data = state.projectsCustomer,
                success = {
                    LeadSixMothProjects(
                        modifier = Modifier.height(600.dp),
                        projects = it
                    )
                },
                retry = {

                }
            )
        }
        item {
            GenericContentLoading(
                data = state.customersUser,
                success = {
                    TypeClientLazy(
                        categoryItem = it
                    )
                },
                retry = {

                }
            )
        }
        item {
            GenericContentLoading(
                modifier = Modifier.height(600.dp),
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
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp).background(
                    Color.White,
                    shape = RoundedCornerShape(30.dp)
                ).padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ejecutivos de ventas",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
        }
        item {
            PagerItemEjecutivoDeVentasDesktop(
                modifier = Modifier.padding(vertical = 16.dp).padding(bottom = 16.dp),
                state = state,
                onAction = onAction
            )
        }
        item {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}