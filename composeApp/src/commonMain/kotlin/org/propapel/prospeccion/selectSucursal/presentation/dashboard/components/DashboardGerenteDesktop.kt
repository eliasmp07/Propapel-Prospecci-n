package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteAction
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.DashboardGerenteState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date

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
        ){
            CardSucursalDesktop(
                modifier = Modifier.weight(1f),
                sucursalId = 10
            )
            GenericContentLoading(
                modifier = Modifier.height(150.dp).weight(0.3f),
                data = state.customersUser,
                success = {
                    CardInfoDesktop(
                        title = "Citas",
                        value = 19,
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
                    CardInfoDesktop(
                        title = "Proyectos",
                        value = 19,
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
                    CardInfoDesktop(
                        title = "Clientes",
                        value = 19,
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
                    CardInfoDesktop(
                        title = "Venta perdidas",
                        value = 19,
                        imageTypeCard = Res.drawable.calendar_date
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