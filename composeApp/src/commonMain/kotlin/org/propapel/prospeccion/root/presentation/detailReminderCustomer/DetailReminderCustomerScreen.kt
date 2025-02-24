package org.propapel.prospeccion.root.presentation.detailReminderCustomer

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.components.CommentForAppointmentCard
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.components.CustomerDateDetailCard
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.components.DateDetailCard

@Composable
fun DetailReminderCustomerScreenRoot(
    viewModel: DetailReminderCustomerViewModel,
    onCompleteReminder:(String) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    DetailReminderCustomerScreen(
        state = state,
        onAction = { action ->
            when (action) {
                DetailReminderCustomerAction.OnBackClick -> onBack()
                is DetailReminderCustomerAction.OnCompleteReminderClick -> onCompleteReminder(action.reminderId)
            }
        }
    )
}

@Composable
private fun DetailReminderCustomerScreen(
    state: DetailReminderCustomerState,
    onAction: (DetailReminderCustomerAction) -> Unit
) {
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
                        onAction(DetailReminderCustomerAction.OnBackClick)
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
                    modifier = Modifier.fillMaxSize().padding().background(
                        Brush.verticalGradient(
                            0f to PrimaryYellowLight,
                            0.6f to SoporteSaiBlue30,
                            1f to MaterialTheme.colorScheme.primary
                        )
                    ),
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp).fillMaxSize()
                            .padding(top = innerPadding.calculateTopPadding())
                            .fillMaxSize().background(
                                Color.White,
                                shape = RoundedCornerShape(
                                    30.dp
                                )
                            )
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(10.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Detalle de tu cita",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Black
                                )
                            }
                        }
                        item {
                            DateDetailCard(
                                state = state
                            )
                        }
                        item {
                            CustomerDateDetailCard(
                                state = state
                            )
                        }
                        item {
                            CommentForAppointmentCard(
                                state = state
                            )
                        }

                    }
                }
            }
        }
    }
}

fun yourAppointmentIsToday(yourDate: LocalDate): String {
    // Obtén la fecha actual
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    // Calcula la diferencia en días
    val daysUntil = today.daysUntil(yourDate)

    return when {
        daysUntil == 0 -> "Tu cita es hoy"
        daysUntil == 1 -> "Tu cita es mañana"
        else -> "Tu cita es el día ${yourDate.dayOfWeekSpanish().lowercase()}"
    }
}


fun LocalDate.dayOfWeekSpanish(): String {
    return when (this.dayOfWeek) {
        DayOfWeek.MONDAY -> "Lunes"
        DayOfWeek.TUESDAY -> "Martes"
        DayOfWeek.WEDNESDAY -> "Miercoles"
        DayOfWeek.THURSDAY -> "Jueves"
        DayOfWeek.FRIDAY -> "Viernes"
        DayOfWeek.SATURDAY -> "Sabado"
        DayOfWeek.SUNDAY -> "Domingo"
        else -> "Domingo"
    }
}
