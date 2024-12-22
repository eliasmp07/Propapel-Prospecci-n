@file:OptIn(
    ExperimentalResourceApi::class,
    ExperimentalMaterial3WindowSizeClassApi::class
)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPink
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPinkBlended
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date
import prospeccion.composeapp.generated.resources.user_info

@Composable
fun HadDateWithClientScreen(
    modifier: Modifier = Modifier,
    state: AddLeadState,
    onAction: (AddLeadAction) -> Unit
) {

    val windowSizeClass = calculateWindowSizeClass()

    if (windowSizeClass.isMobile) {

        Column(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    0f to PrimaryPinkBlended,
                    1f to PrimaryPink
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
                text = "¿Es tu primera interacción?",
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
                    onAction(AddLeadAction.OnNextScreenClick(ContainerState.ADD_INTERACTION_CUSTOMER))
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
    } else {
        HadDateWithClienteDesktopScreen(
            onAction = onAction
        )
    }


}

@Composable
private fun HadDateWithClienteDesktopScreen(
    onAction: (AddLeadAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryPinkBlended,
                1f to PrimaryPink
            )
        ).padding(16.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End).padding(16.dp).pointerHoverIcon(PointerIcon.Hand),
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
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.weight(0.5f).aspectRatio(3f),
                painter = painterResource(
                    Res.drawable.calendar_date
                ),
                contentDescription = null
            )
            Column(
                modifier = Modifier.weight(0.5f).verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "¿Es tu primera interacción?",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                ProSalesActionButton(
                    text = "Si",
                    isLoading = false,
                    textColor = Color.White,
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        onAction(AddLeadAction.OnNextScreenClick(ContainerState.ADD_INTERACTION_CUSTOMER))
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
                        onAction(AddLeadAction.OnNextScreenClick(ContainerState.WANT_BOOK_AN_APPOINTMENT))
                    }
                )
            }
        }

    }
}