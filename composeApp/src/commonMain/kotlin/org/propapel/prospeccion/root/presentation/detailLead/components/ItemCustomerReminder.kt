package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.ui.typeHour
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.dashboard.components.monthGet
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.dayOfWeekSpanish

@Composable
fun ItemCustomerReminder(
    modifier: Modifier = Modifier,
    reminder: Reminder,
    isExpanded: Boolean = false,
    customer: Customer,
    onDetailReminder: (String) -> Unit
) {
    val reminderDate = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong()).toLocalDateTime(TimeZone.UTC)
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(
                if(isExpanded) 0.dp else 20.dp
            ),
            onClick = {
                onDetailReminder(reminder.reminderId.toString())
            },
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp),
            elevation = CardDefaults.elevatedCardElevation(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side with the gradient and month
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(50.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFA726),  // Naranja claro
                                    Color(0xFFFF5722)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
                // Right side with the date and schedule
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mes: ",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = monthGet(reminderDate.monthNumber),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dia: ",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = reminderDate.date.dayOfWeekSpanish(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
                Text(text = "${reminderDate.hour}: ${reminderDate.minute} ${typeHour(reminderDate.hour)}", style = MaterialTheme.typography.titleSmall)
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                        .background(
                            Color.Yellow,
                            shape = RoundedCornerShape(50)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }
    }
}