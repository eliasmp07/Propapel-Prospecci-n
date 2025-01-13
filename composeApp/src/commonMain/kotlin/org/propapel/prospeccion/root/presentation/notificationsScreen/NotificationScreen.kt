package org.propapel.prospeccion.root.presentation.notificationsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.GroupWork
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.converteDate
import org.propapel.prospeccion.core.presentation.ui.extensions.toFormatStringDate
import org.propapel.prospeccion.core.presentation.ui.extensions.toFormatStringTime
import org.propapel.prospeccion.core.presentation.ui.extensions.toLocalDateTime
import org.propapel.prospeccion.root.domain.models.Reminder
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_ref

@Composable
fun NotificationScreenRoot(reminders: List<Reminder>){
    NotificationScreen(reminders)
}

@Composable
fun NotificationScreen(reminders: List<Reminder>){
    LazyColumn (
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ){
        items(reminders){
            ItemAlert(
                reminder = it
            )
        }
    }
}

@Composable
private fun ItemAlert(reminder: Reminder){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomerImage(
                reminder = reminder
            )
            Column {
                Text(
                    text = "Tienes programada una cita",
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = "Haz programando una cita con ${reminder.customer.companyName}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = reminder.reminderDate.toLong().toLocalDateTime().toFormatStringDate(),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = reminder.reminderDate.toLong().toLocalDateTime().toFormatStringTime(),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun RowScope.CustomerImage(
    reminder: Reminder
){
    Box(
        modifier = Modifier.size(
            70.dp
        ),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier.size(
                70.dp
            ),
            painter = painterResource(Res.drawable.customer_ref),
            contentDescription = null,
        )
        BadgeTypeAppointment(reminder)
    }
}

@Composable
private fun BoxScope.BadgeTypeAppointment(
    reminder: Reminder
){
    Box(
        modifier = Modifier.align(Alignment.BottomEnd).size(32.dp).background(
            SoporteSaiBlue,
            shape = CircleShape
        ).clip(CircleShape),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = detectIconTypeAppointment(reminder.typeAppointment),
            contentDescription = null,
            tint = Color.White
        )
    }
}


fun detectIconTypeAppointment(type: String): ImageVector{
    return when(type){
        "Presencial" -> Icons.Rounded.GroupWork
        "Reunion Remota" -> Icons.Rounded.Call
        else -> Icons.Rounded.NotificationsActive
    }
}