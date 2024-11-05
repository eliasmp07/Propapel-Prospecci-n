package org.propapel.prospeccion.root.presentation.detailReminderCustomer.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPinkBlended
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPinkLight
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryVioletDark
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryVioletLight
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterFromLeft
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterRight
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.DetailReminderCustomerState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.notes_appointment

@Composable
fun CommentForAppointmentCard(
    modifier: Modifier = Modifier,
    state: DetailReminderCustomerState
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateEnterRight(
                durationMillis = 2000
            )
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            elevation = CardDefaults.elevatedCardElevation(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Left side with the gradient and month
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    PrimaryVioletLight,
                                    PrimaryVioletDark
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Notas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .rotate(-90f)
                    )
                }
                // Right side with the date and schedule
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Text(
                        text = "Notas de para la proxima cita",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Text(
                        text = state.remindersDay.description,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

            }
        }
        Image(
            modifier = Modifier.align(Alignment.BottomEnd)
                .offset(y = 10.dp).size(80.dp).rotate(90f),
            painter = painterResource(Res.drawable.notes_appointment),
            contentDescription = null
        )
    }
}