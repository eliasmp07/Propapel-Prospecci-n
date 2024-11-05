package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPink
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPinkBlended
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil

@Composable
fun HeIsInterestedInAProduct(
    modifier: Modifier = Modifier,
    state: AddLeadState,
    onAction: (AddLeadAction) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFF6363),
                    Color(0xFFAB47BC)
                )
            )
        ).padding(16.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End).padding(16.dp),
            onClick = {
                onAction(AddLeadAction.OnBackClick)
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "¿En tu ${whatIsTypeDate(state.interactionType)} el cliente se intereso en un producto?",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier.weight(0.2f)
        )
        KottieAnimationUtil(
            modifier = Modifier.size(350.dp).align(Alignment.CenterHorizontally),
            fileRoute = "files/anim_interview.json"
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        ProSalesActionButton(
            text = "Si",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.ADD_INFO_INTERESED_CLIENTE))
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ProSalesActionButtonOutline(
            text = "No",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.WANT_BOOK_AN_APPOINTMENT))
            }
        )

    }


}

fun whatIsTypeDate(date: InteractionType): String{
    return when(date){
        InteractionType.PRESENCIAL -> "visita"
        InteractionType.LLAMADA -> "entrevista"
        InteractionType.REUNION_REMOTA -> "reunion remota"
        InteractionType.EMAIL -> "entrevista"
    }
}