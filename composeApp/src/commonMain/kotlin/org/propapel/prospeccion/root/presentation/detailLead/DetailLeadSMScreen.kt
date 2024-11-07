package org.propapel.prospeccion.root.presentation.detailLead

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterBottom
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterFromLeft
import org.propapel.prospeccion.root.presentation.detailLead.components.CreateReminderDialog
import org.propapel.prospeccion.root.presentation.detailLead.components.InfoLeadPagerScreen
import org.propapel.prospeccion.root.presentation.detailLead.components.NotificationPager
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date
import prospeccion.composeapp.generated.resources.cita_client
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.notes_appointment
import prospeccion.composeapp.generated.resources.products

@Composable
fun DetailCustomerSMScreenRoot(
    viewModel: DetailLeadViewModel,
    onUpdateCustomer: (String) -> Unit,
    onAddInteractions: (String) -> Unit,
    onDetailReminderLead: (String) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    DetailCustomerSMScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is DetailLeadAction.OnDetailReminderCustomer -> onDetailReminderLead(action.idReminder)
                is DetailLeadAction.AddInteractionsClick -> onAddInteractions(action.idCustomer)
                is DetailLeadAction.OnUpdateCustomerClick -> onUpdateCustomer(action.idCustomer)
                DetailLeadAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun DetailCustomerSMScreen(
    state: DetailLeadSMState,
    onAction: (DetailLeadAction) -> Unit,
    pages: Array<NotificationPager> = NotificationPager.entries.toTypedArray()

) {

    val pagerState = rememberPagerState(pageCount = { pages.size })
    // Estado para la imagen a mostrar
    var currentImageResource by remember { mutableStateOf(Res.drawable.customer_person) }
    var currentText by remember { mutableStateOf("") }

    LaunchedEffect(pagerState.currentPage) {
        currentImageResource = when (pagerState.currentPage) {
            0 -> Res.drawable.customer_person // Cambia según la página
            1 -> Res.drawable.cita_client // Imagen para la página 1
            2 -> Res.drawable.calendar_date// Imagen para la página 2
            else -> Res.drawable.products // Por defecto
        }
        currentText  = when(pagerState.currentPage){
            0 -> "Informacion del cliente"
            1 -> "Interacciones"
            2 -> "Proximas citas"
            else -> "Productos interesados"
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            ) {
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        onAction(DetailLeadAction.OnBackClick)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Crossfade(
            targetState = state.isLoading,
            animationSpec = tween(
                durationMillis = 350
            )
        ) { tarjetValue ->
            if (tarjetValue) {
                LoadingPropapel()
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            0f to PrimaryYellowLight,
                            0.6f to SoporteSaiBlue30,
                            1f to MaterialTheme.colorScheme.primary
                        )
                    ),
                ) {
                    Box(
                        modifier = Modifier.weight(0.3f).fillMaxWidth()
                    ) {
                        Crossfade(
                            modifier = Modifier.padding(16.dp).align(Alignment.CenterStart),
                            targetState = currentText,
                            animationSpec = tween(durationMillis = 500) // Duración de la animación
                        ) { text ->
                            Text(
                                text = text,
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                        Crossfade(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            targetState = currentImageResource,
                            animationSpec = tween(durationMillis = 0) // Duración de la animación
                        ) { image ->
                            Image(
                                modifier = Modifier.size(150.dp).animateEnterBottom(),
                                painter = painterResource(image),
                                contentDescription = null
                            )
                        }
                    }
                    ElevatedCard(
                        modifier = Modifier.weight(0.7f).fillMaxWidth().animateEnterBottom(initialOffsetY = 100f),
                        shape = RoundedCornerShape(
                            topEnd = 30.dp,
                            topStart = 30.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        InfoLeadPagerScreen(
                            customer = state.customer,
                            pagerState = pagerState,
                            pages = pages,
                            onAction = onAction
                        )
                    }
                }
            }
        }
    }
    if (state.showCreateDate){
        CreateReminderDialog(
            state = state,
            onAction = onAction,
            onDismissRequest = {
                onAction(DetailLeadAction.OnToggleCreateAppointmentDialog)
            }
        )
    }
}