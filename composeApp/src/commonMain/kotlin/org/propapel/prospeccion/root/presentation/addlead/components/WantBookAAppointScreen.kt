@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPink
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.GenericContentWindowsSize
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.dashboard.isMobile

@Composable
fun WantBookAAppointScreen(
    onAction: (AddLeadAction) -> Unit
){
    val windowSizeClass = calculateWindowSizeClass()

    if (windowSizeClass.isMobile){
        WantBookAAppointMobileScreen(
            onAction = onAction
        )
    }else{
        GenericContentWindowsSize(
            brush = Brush.verticalGradient(
                colors = listOf(
                    PrimaryYellowLight,
                    PrimaryPink
                )
            ),
            content1 = {
                KottieAnimationUtil(
                    modifier = Modifier.weight(0.5f).padding(12.dp).aspectRatio(3f),
                    fileRoute = "files/anim_had_appointment.json"
                )
            },
            content2 = {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "¿Quieres agendar una cita?",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                ProSalesActionButton(
                    text = "Si",
                    shape = RoundedCornerShape(12.dp),
                    textColor = Color.White,
                    isLoading = false,
                    onClick = {
                        onAction(AddLeadAction.OnNextScreenClick(ContainerState.ADD_INFO_REMINDER_APPOINTMENT))
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ProSalesActionButtonOutline(
                    text = "No",
                    shape = RoundedCornerShape(12.dp),
                    isLoading = false,
                    onClick = {
                        onAction(AddLeadAction.OnNextScreenClick(ContainerState.FINISH))
                    }
                )
            },
            onCloseScreen = {
                onAction(AddLeadAction.OnBackClick)
            }
        )
    }
}

@Composable
private fun WantBookAAppointMobileScreen(
    onAction: (AddLeadAction) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                colors = listOf(
                    PrimaryYellowLight,
                    PrimaryPink
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
            text = "¿Quieres agendar una cita?",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        KottieAnimationUtil(
            modifier = Modifier.size(350.dp).align(Alignment.CenterHorizontally),
            fileRoute = "files/anim_had_appointment.json"
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        ProSalesActionButton(
            text = "Si",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.ADD_INFO_REMINDER_APPOINTMENT))
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ProSalesActionButtonOutline(
            text = "No",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.FINISH))
            }
        )
    }
}