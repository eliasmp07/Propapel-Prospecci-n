@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.root.presentation.detailLead

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import contentScale.ContentScale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterBottom
import org.propapel.prospeccion.core.presentation.ui.extensions.isTrue
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.detailLead.components.CreateReminderDialog
import org.propapel.prospeccion.root.presentation.detailLead.components.DialogConfirmOption
import org.propapel.prospeccion.root.presentation.detailLead.components.InfoLeadPagerScreen
import org.propapel.prospeccion.root.presentation.detailLead.components.ModalBottomCloseAppointment
import org.propapel.prospeccion.root.presentation.detailLead.components.ModalBottomSheetDeleteProject
import org.propapel.prospeccion.root.presentation.detailLead.components.ModalBottomStateProject
import org.propapel.prospeccion.root.presentation.detailLead.components.NotificationPager
import org.propapel.prospeccion.root.presentation.detailLead.components.UpdateReminderDialog
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date
import prospeccion.composeapp.generated.resources.cita_client
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.products
import prospeccion.composeapp.generated.resources.project_confirm

@Composable
fun DetailCustomerSMScreenRoot(
    viewModel: DetailLeadViewModel,
    onUpdateCustomer: (String) -> Unit,
    onCreateProject: (String) -> Unit,
    onAddInteractions: (String) -> Unit,
    onDetailReminderLead: (String) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    DetailCustomerSMScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is DetailLeadAction.OnCreateProject -> onCreateProject(action.customerId)
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

    val sheetState = rememberModalBottomSheetState()
    val sheetStateProject = rememberModalBottomSheetState()
    val sheetStateCloseAppointment = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()

    var showBottomSheet by remember { mutableStateOf(false) }

    var showBottomSheetInfoProject by remember { mutableStateOf(false) }

    var showBottomSheetCloseAppointment by remember {
        mutableStateOf(false)
    }

    val pagerState = rememberPagerState(pageCount = { pages.size })
    // Estado para la imagen a mostrar
    var currentImageResource by remember { mutableStateOf(Res.drawable.customer_person) }
    var currentText by remember { mutableStateOf("") }

    LaunchedEffect(pagerState.currentPage) {
        currentImageResource = when (pagerState.currentPage) {
            0 -> Res.drawable.customer_person // Cambia según la página
            1 -> Res.drawable.cita_client // Imagen para la página 1
            2 -> Res.drawable.calendar_date // Imagen para la página 2
            3 -> Res.drawable.products
            else -> Res.drawable.project_confirm // Por defecto
        }
        currentText = when (pagerState.currentPage) {
            0 -> "Informacion del cliente"
            1 -> "Interacciones"
            2 -> "Proximas citas"
            3 -> "Productos de interes"
            else -> "Proyectos"
        }
    }

    LaunchedEffect(
        state.successDelete,
    ) {
        if (state.successDelete) {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }

        }
    }
    LaunchedEffect(state.isError) {
        if (state.isError) {
            if (state.congratulationsCloseProject) {
                delay(4000)
            } else {
                delay(2000) // Espera de 2 segundos
            }
            onAction(DetailLeadAction.HideError)
        }
    }
    Box {
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
                                    style = MaterialTheme.typography.titleMedium,
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
                                state = state,
                                customer = state.customer,
                                pagerState = pagerState,
                                pages = pages,
                                onCloseAppointment = {
                                    onAction(DetailLeadAction.OnCloseReminder(it))
                                    showBottomSheetCloseAppointment = true
                                },
                                onDeleteProject = {
                                    onAction(DetailLeadAction.OnDeleteProject(it))
                                    scope.launch {
                                        sheetStateProject.expand()
                                    }
                                    showBottomSheetInfoProject = true
                                },
                                projects = state.project,
                                onAction = onAction
                            )
                        }
                        if (showBottomSheetInfoProject) {
                            ModalBottomStateProject(
                                sheetState = sheetStateProject,
                                state = state,
                                onDismissRequest = {
                                    showBottomSheetInfoProject = false
                                    onAction(DetailLeadAction.OnDeleteProject(Project()))
                                },
                                onCloseProject = {
                                    showBottomSheetInfoProject = false
                                    onAction(DetailLeadAction.OnCloseProject)
                                },
                                onDeleteProject = {
                                    showBottomSheetInfoProject = false
                                    showBottomSheet = true
                                }
                            )
                        }
                        if (showBottomSheetCloseAppointment) {
                            ModalBottomCloseAppointment(
                                state = state,
                                sheetState = sheetStateCloseAppointment,
                                scope = scope,
                                onAction = onAction,
                                onDismissRequest = {
                                    showBottomSheetCloseAppointment = false
                                }
                            )
                        }


                    }
                }
                if (state.showCancelNotification) {
                    DialogConfirmOption(
                        title = "Eliminar recordatorio",
                        description = "¿Estas seguro de eliminar el recordatorio?",
                        textButton = "Eliminar",
                        onAcceptOption = {
                            onAction(DetailLeadAction.OnConfirmCancelarReminder)
                        },
                        onDismissRequest = {
                            onAction(DetailLeadAction.OnToggleDeleteReminderConfirm)
                        }
                    )
                }
                if (state.showDialogUpdateReminder) {
                    UpdateReminderDialog(
                        state = state,
                        onAction = onAction,
                        onDismissRequest = {
                            onAction(DetailLeadAction.OnDimissUpdateReminder)
                        }
                    )
                }
                if (state.showCreateDate) {
                    CreateReminderDialog(
                        state = state,
                        onAction = onAction,
                        onDismissRequest = {
                            onAction(DetailLeadAction.OnToggleCreateAppointmentDialog)
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = state.isError,
            enter = slideInVertically(
                initialOffsetY = { it } // Aparece de abajo hacia arriba
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it } // Desaparece de arriba hacia abajo
            ) + fadeOut(),
        ) {
            Box(
                modifier = Modifier
            ) {
                if (state.congratulationsCloseProject) {
                    KottieAnimationUtil(
                        fileRoute = "files/sucess_lead_meta.json",
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        interations = 3,
                        contentScale = ContentScale.Crop
                    )
                }
                ElevatedCard(
                    modifier = Modifier.padding(10.dp).align(Alignment.BottomCenter),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = state.errorColor
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = state.error.asString(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheetDeleteProject(
            sheetState = sheetState,
            state = state,
            onDismissRequest = {
                showBottomSheet = false
                onAction(DetailLeadAction.OnDeleteProject(Project()))
            },
            onAction = onAction,
            onDeleteProject = {
                onAction(DetailLeadAction.OnConfirmProject)
            }
        )
    }
}