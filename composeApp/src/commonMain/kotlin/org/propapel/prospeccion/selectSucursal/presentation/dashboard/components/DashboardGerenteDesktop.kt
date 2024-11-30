package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteAction
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteState

@Composable
fun DashboardGerenteDesktop(
    state: DashboardGerenteState,
    onAction: (DashboardGerenteAction) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GenericContentLoading(
                modifier = Modifier.height(200.dp).weight(1f),
                data = state.customersUser,
                success = {
                    CardGenericInfoDesktop(
                        modifier = Modifier.height(200.dp),
                        customers = it,
                        title = "Total de clientes"
                    )
                },
                retry = {

                }
            )
            GenericContentLoading(
                modifier = Modifier.height(200.dp).weight(1f),
                data = state.customersUser,
                success = {
                    val filterNews = it.filter { client ->
                        client.typeOfClient == TypeOfClient.NUEVO.name
                    }
                    CardGenericInfoDesktop(
                        modifier = Modifier.height(200.dp),
                        colorCustomer = SuccessGreen,
                        customers = filterNews,
                        title = "Clientes Nuevos"
                    )
                },
                retry = {

                }
            )
            GenericContentLoading(
                modifier = Modifier.height(200.dp).weight(1f),
                data = state.customersUser,
                success = {
                    val filterDesarrolllo = it.filter { client ->
                        client.typeOfClient == TypeOfClient.DESARROLLO.name
                    }
                    CardGenericInfoDesktop(
                        modifier = Modifier.height(200.dp),
                        colorCustomer = Color(0xFFFF9800),
                        customers = filterDesarrolllo,
                        title = "Clientes en desarrollo"
                    )
                },
                retry = {

                }
            )
            GenericContentLoading(
                modifier = Modifier.height(200.dp).weight(1f),
                data = state.customersUser,
                success = {
                    val filterRecuperacion = it.filter { client ->
                        client.typeOfClient == TypeOfClient.RECUPERACIÓN.name
                    }
                    CardGenericInfoDesktop(
                        modifier = Modifier.height(200.dp),
                        colorCustomer = Color.Red.copy(alpha = 0.4f),
                        customers = filterRecuperacion,
                        title = "Clientes en recuperación"
                    )
                },
                retry = {

                }
            )
        }
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