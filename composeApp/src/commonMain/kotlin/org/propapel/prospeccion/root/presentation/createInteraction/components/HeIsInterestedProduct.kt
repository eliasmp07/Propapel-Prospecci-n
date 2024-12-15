package org.propapel.prospeccion.root.presentation.createInteraction.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.GenericContentWindowsSize
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.addlead.components.whatIsTypeDate
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionAction
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionLeadState
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionScreenState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.interesting_product_img

@Composable
fun HeIsInterestedProduct(
    state: CreateInteractionLeadState,
    isMobile: Boolean,
    onAction: (CreateInteractionAction) -> Unit
) {

    if (isMobile) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(
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
                    onAction(CreateInteractionAction.OnBackClick)
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
                text = "¿En tu ${whatIsTypeDate(state.typeClient)} el cliente se intereso en un producto?",
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
                    onAction(CreateInteractionAction.OnNextScreen(CreateInteractionScreenState.Add_Products))
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            ProSalesActionButtonOutline(
                text = "No",
                isLoading = false,
                onClick = {
                    onAction(CreateInteractionAction.OnNextScreen(CreateInteractionScreenState.FINISH))
                }
            )

        }
    } else {
        GenericContentWindowsSize(
            onCloseScreen = {
                onAction(CreateInteractionAction.OnBackClick)
            },
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFF6363),
                    Color(0xFFAB47BC)
                )
            ),
            content1 = {
                Box(
                    modifier = Modifier.weight(0.5f).padding(30.dp).height(400.dp).clip(CircleShape)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(Res.drawable.interesting_product_img),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                }
            },
            content2 = {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "¿En tu ${whatIsTypeDate(state.typeClient)} el cliente se intereso en un producto?",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                ProSalesActionButton(
                    shape = RoundedCornerShape(12.dp),
                    text = "Si",
                    textColor = Color.White,
                    isLoading = false,
                    onClick = {
                        onAction(CreateInteractionAction.OnNextScreen(CreateInteractionScreenState.Add_Products))
                    }
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                ProSalesActionButtonOutline(
                    text = "No",
                    shape = RoundedCornerShape(12.dp),
                    isLoading = false,
                    onClick = {
                        onAction(CreateInteractionAction.OnNextScreen(CreateInteractionScreenState.FINISH))
                    }
                )
            }
        )
    }
}