package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPinkBlended
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellow
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.user_info

@Composable
fun AddMoreInfoLead(
    state: AddLeadState,
    onAction: (AddLeadAction) -> Unit
){
    Column(
        modifier = Modifier .background(
            Brush.verticalGradient(
                0f to PrimaryPinkBlended,
                0.6f to PrimaryYellowLight,
                1f to PrimaryYellow
            )
        ).padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())

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
            text = "¿Desea capturar más información del cliente?",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally).size(150.dp),
            painter = painterResource(
                Res.drawable.user_info
            ),
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        ProSalesActionButton(
            text = "Si",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnResponseOportunityChange(true))
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.HAD_INTERACTION_CUSTOMER))
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ProSalesActionButtonOutline(
            text = "No",
            isLoading = false,
            onClick = {

                onAction(AddLeadAction.OnResponseOportunityChange(false))
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.FINISH))
            }
        )
    }
}