@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.dates.components.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.ui.extensions.minusMonths
import org.propapel.prospeccion.core.presentation.ui.extensions.plusMonths
import org.propapel.prospeccion.core.presentation.ui.utils.generateDatesForMonth
import org.propapel.prospeccion.root.presentation.dates.CalendarView
import org.propapel.prospeccion.root.presentation.dates.DatesAction
import org.propapel.prospeccion.root.presentation.dates.DatesSMState
import org.propapel.prospeccion.root.presentation.dates.DisplayAppointments

@Composable
fun DatesScreenMobile(
    state: DatesSMState,
    onAction: (DatesAction) -> Unit
) {
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    var currentMonth by remember { mutableStateOf(currentDateTime.date) }

    // Estado para almacenar la fecha seleccionada
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }


    val pullRefreshState =
        rememberPullRefreshState(
            state.isRefreshing,
            { onAction(DatesAction.OnRefresh) })


    Box(
        modifier = Modifier.fillMaxSize() .pullRefresh(pullRefreshState).background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                val dates = generateDatesForMonth(
                    currentMonth,
                    startFromSunday = true
                )

                CalendarView(
                    datesReminder = state.datesReminders,
                    month = currentMonth,
                    date = dates,
                    displayNext = true,
                    displayPrev = true,
                    onClickNext = { currentMonth = currentMonth.plusMonths(1) },
                    onClickPrev = { currentMonth = currentMonth.minusMonths(1) },
                    onClick = { date ->
                        if (selectedDate != null && selectedDate == date) {
                            selectedDate = null
                        } else {
                            selectedDate = date
                        }
                    }, // Actualiza la fecha seleccionada
                    startFromSunday = false
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(
                            shape = CircleShape,
                            color = Color.Red.copy(alpha = 0.5f)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Días ocupados",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier.size(24.dp).background(
                            shape = CircleShape,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Días libres",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
                // Mostrar las citas si hay una fecha seleccionada
                selectedDate?.let { date ->
                    DisplayAppointments(
                        date,
                        state.datesReminders,
                        onDetailReminderCustomer = {
                            onAction(DatesAction.OnDetailReminder(it))
                        },
                        state.reminders
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

