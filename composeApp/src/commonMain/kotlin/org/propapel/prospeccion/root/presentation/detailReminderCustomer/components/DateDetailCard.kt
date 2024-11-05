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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterRight
import org.propapel.prospeccion.root.presentation.dashboard.components.monthGet
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.DetailReminderCustomerState
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.dayOfWeekSpanish
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.yourAppointmentIsToday
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date

@Composable
fun DateDetailCard(
    modifier: Modifier = Modifier,
    state: DetailReminderCustomerState
){
    Box(
        modifier = Modifier
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
                .height(150.dp),
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
                                    Color(0xFFFF6363),
                                    Color(0xFFAB47BC)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = monthGet(state.date.monthNumber),
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
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.date.dayOfWeekSpanish(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                    Text(
                        text = state.date.dayOfMonth.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    Color.Yellow,
                                    shape = RoundedCornerShape(50)
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = yourAppointmentIsToday(state.date),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
        }
        Image(
            modifier = Modifier.align(Alignment.BottomEnd)
                .offset(y = 10.dp)
                .rotate(10f).size(80.dp),
            painter = painterResource(Res.drawable.calendar_date),
            contentDescription = null
        )
    }

}