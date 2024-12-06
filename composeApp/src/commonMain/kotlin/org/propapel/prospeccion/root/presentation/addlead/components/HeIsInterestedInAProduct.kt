@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.GenericContentWindowsSize
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.background_intro
import prospeccion.composeapp.generated.resources.interesting_product_img

@Composable
fun HeIsInterestedInAProduct(
    onAction: (AddLeadAction) -> Unit
) {

    val windowSizeClass = calculateWindowSizeClass()

    if(windowSizeClass.isMobile){
        HeIsInterestedAnProductMobileScreen(
            onAction = onAction
        )
    }else{
        GenericContentWindowsSize(
            onCloseScreen = {
                onAction(AddLeadAction.OnBackClick)
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
                ){
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
                    text = "¿El cliente se intereso en algun producto o servicio?",
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
                        onAction(AddLeadAction.OnNextScreenClick(ContainerState.ADD_INFO_INTERESED_CLIENTE))
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
                        onAction(AddLeadAction.OnNextScreenClick(ContainerState.WANT_BOOK_AN_APPOINTMENT))
                    }
                )
            }
        )
    }


}

@Composable
fun HeIsInterestedAnProductMobileScreen(
    onAction: (AddLeadAction) -> Unit
){
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
            text = "¿El cliente se intereso en algun producto o servicio?",
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