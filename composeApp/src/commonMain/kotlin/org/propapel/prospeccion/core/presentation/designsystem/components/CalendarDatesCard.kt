package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMAction
import org.propapel.prospeccion.root.presentation.dashboard.DashboardSMState
import org.propapel.prospeccion.root.presentation.dashboard.components.ItemUserDate
import org.propapel.prospeccion.root.presentation.dashboard.components.monthGet
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading

@Composable
fun CalendarDatesCard(
    state: DashboardSMState = DashboardSMState(),
    modifier: Modifier = Modifier,
    onAction: (DashboardSMAction) -> Unit = {}
) {
    GenericContentLoading(
        modifier = modifier,
        data = state.reminders,
        retry = {

        },
        success = {
            val filteredReminders = remember(state.date) {
                // Filtrar los recordatorios en base a la fecha seleccionada
                it.filter { reminder ->
                    // Convertir el timestamp de long a LocalDate
                    val reminderDate = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong())
                        .toLocalDateTime(TimeZone.UTC).date

                    // Comparar solo el día, mes y año
                    reminderDate.year == state.date.year &&
                            reminderDate.monthNumber == state.date.monthNumber &&
                            reminderDate.dayOfMonth == state.date.dayOfMonth && reminder.isCompleted == false
                }
            }

            ElevatedCard(
                shape = RoundedCornerShape(20.dp),
                modifier = modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(15.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    val currentDateTime =
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

                    // Extraer el mes actual y el año
                    val currentMonth = currentDateTime.month
                    val currentYear = currentDateTime.year

                    // Mostrar el mes y el año actuales
                    Text(
                        text = "${monthGet(currentMonth.number)} $currentYear",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Usamos la fecha actual para mainDate y selectedDate en HomeDateSelector
                    val currentDate = currentDateTime.date

                    HomeDateSelector(
                        selectedDate = state.date, // Fecha actual seleccionada
                        mainDate = currentDate, // Usar la fecha actual como mainDate
                        onDateClick = {
                            onAction(DashboardSMAction.OnDateChange(it))
                        }
                    )

                    // Verificar si hay recordatorios filtrados
                    if (filteredReminders.isEmpty()) {
                        // Mostrar mensaje de que no hay citas
                        Text(
                            text = "No hay citas el dia de hoy",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                                .padding(vertical = 16.dp)
                        )
                    } else {
                        // Mostrar los recordatorios filtrados
                        filteredReminders.forEach { reminder ->
                            ItemUserDate(
                                reminder = reminder,
                                onDetailReminder = {
                                    onAction(
                                        DashboardSMAction.OnDetailReminderCustomer(it)
                                    )
                                }) // Pasa el recordatorio para mostrar los datos
                        }
                    }
                }
            }
        }
    )

}
