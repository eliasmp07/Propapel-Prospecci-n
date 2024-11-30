package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlack
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiWhite
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.ui.extensions.previousMoth
import org.propapel.prospeccion.selectSucursal.domain.model.CustomerUser
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person

@Composable
fun CardGenericInfoDesktop(
    modifier: Modifier = Modifier,
    title: String,
    colorCustomer: Color = SoporteSaiBlue,
    customers: List<CustomerUser>,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ){
            Content(title = title, count = customers.size, color = colorCustomer)
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            HorizontalDivider()
            ValidateLastMothContent(customers = customers)
        }
    }

}

@Composable
fun ValidateLastMothContent(
    modifier: Modifier = Modifier,
    customers: List<CustomerUser>
) {
    val date = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
    val lastMoth = date.previousMoth()
    val ( isLow, infoDifference) = infoValidate(
        customers,
        mothLast = lastMoth,
        currentMoth = date.monthNumber
    )
    Row(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContentValidateLastMoth(
            isLow = !isLow,
            infoDiference = infoDifference
        )
        Text(
            text = "Del mes anterior",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

fun infoValidate(
    customer: List<CustomerUser>,
    mothLast: Int,
    currentMoth: Int
): Pair<Boolean, String> {
    var infoDiference = ""
    var mothLastCount = 0
    var mothCurrentCount = 0
    var isImproveThisCount = false

    customer.forEach {
        when (it.createdAt.monthNumber) {
            mothLast -> mothLastCount++
            currentMoth -> mothCurrentCount++
        }
    }

    if (mothLastCount > mothCurrentCount) {
        isImproveThisCount = false
        infoDiference = "-${
            calculoPorcentual(
                mothLastCount,
                mothCurrentCount
            )
        }%"

    } else {
        isImproveThisCount = true
        infoDiference = "+${
            calculoPorcentual(
                mothLastCount,
                mothCurrentCount
            )
        }%"
    }

    return Pair(
        isImproveThisCount,
        infoDiference
    )
}

fun calculoPorcentual(
    mothLast: Int,
    mothNow: Int
): Int {
    if (mothLast == 0) {
        return if (mothNow > 0) 100 else 0
    }
    val x = mothNow - mothLast
    val y = x.toDouble() / mothLast
    return (y * 100).toInt()
}


@Composable
fun ContentValidateLastMoth(
    isLow: Boolean,
    infoDiference: String
) {
    val value by rememberInfiniteTransition().animateFloat(
        initialValue = 5f,
        targetValue = -5f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val color = if (isLow) Color.Red else SuccessGreen
    Row(
        modifier = Modifier.background(
            color = color.copy(
                alpha = 0.2f
            ),
            shape = RoundedCornerShape(10.dp)
        ).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier.offset(y = value.dp),
            imageVector = if(!isLow) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
            contentDescription = null,
            tint = color
        )
        Text(
            text = infoDiference,
            color = color
        )
    }
}

@Composable
fun Content(
    title: String = "",
    count: Int,
    color: Color = SoporteSaiBlue,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier.background(
                color = color,
                shape = RoundedCornerShape(30.dp)
            ).size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.customer_person),
                contentDescription = null
            )
        }

    }
}