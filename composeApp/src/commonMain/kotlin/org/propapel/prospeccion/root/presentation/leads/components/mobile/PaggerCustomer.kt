package org.propapel.prospeccion.root.presentation.leads.components.mobile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.presentation.leads.LeadAction
import org.propapel.prospeccion.root.presentation.leads.LeadSMState
import org.propapel.prospeccion.root.presentation.leads.components.ActionIcon
import org.propapel.prospeccion.root.presentation.leads.components.SwipeableItemWithActions

@Composable
fun PaginationControls(
    modifier: Modifier = Modifier,
    page: Int,
    totalPages: Int,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit
) {
    Row(
        modifier = modifier.shadow(
            elevation = 10.dp,
            RoundedCornerShape(30.dp)
        ).fillMaxWidth().clip(RoundedCornerShape(30.dp)).background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousPage) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Página",
            style = MaterialTheme.typography.bodyMedium
        )
        AnimatedContent(
            targetState = page,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { height -> height } + fadeIn() togetherWith
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() togetherWith
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            },
            label = "animated content"
        ) { targetCount ->
            Text(
                text = " $targetCount ",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "de $totalPages",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onNextPage) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.rotate(180f)
            )
        }
    }
}

