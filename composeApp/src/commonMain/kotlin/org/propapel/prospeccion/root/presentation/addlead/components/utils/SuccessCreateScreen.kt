@file:OptIn(ExperimentalResourceApi::class,
            ExperimentalMaterial3WindowSizeClassApi::class
)

package org.propapel.prospeccion.root.presentation.addlead.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiWhite
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.dashboard.isMobile

@Composable
fun SuccessCreateScreen(
    onAction: (AddLeadAction) -> Unit
) {
    val windowSizeClass = calculateWindowSizeClass()
    if (windowSizeClass.isMobile) {
        SuccessCreateScreenMobile(
            onAction = onAction
        )
    }else{
        Box(
            modifier = Modifier.fillMaxSize().background(SuccessGreen),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(0.5f).fillMaxHeight(),
                ){
                    KottieAnimationUtil(
                        modifier = Modifier.fillMaxSize(),
                        fileRoute = "files/anim_lottie_sucess_create.json"
                    )
                    KottieAnimationUtil(
                        modifier = Modifier.aspectRatio(3f).align(Alignment.Center),
                        fileRoute = "files/anim_success_customer_create.json"
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight().weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Cliente creado con exito",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = SoporteSaiWhite
                    )
                    Spacer(
                        modifier = Modifier.height(32.dp)
                    )
                    ProSalesActionButtonOutline(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        text = "Aceptar",
                        textColor = Color.White,
                        borderColor = Color.White,
                        isLoading = false,
                        onClick = {
                            onAction(AddLeadAction.OnBackClick)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessCreateScreenMobile(
    onAction: (AddLeadAction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().background(SuccessGreen),
    ) {
        KottieAnimationUtil(
            modifier = Modifier.fillMaxSize(),
            fileRoute = "files/anim_lottie_sucess_create.json"
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.End).padding(16.dp),
                onClick = {
                    onAction(AddLeadAction.OnBackClick)
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            )
            Spacer(
                modifier = Modifier.weight(0.2f)
            )
            KottieAnimationUtil(
                modifier = Modifier.size(350.dp).align(Alignment.CenterHorizontally),
                fileRoute = "files/anim_success_customer_create.json"
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Cliente creado con exito",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = SoporteSaiWhite
            )
            Spacer(
                modifier = Modifier.weight(0.5f)
            )
            ProSalesActionButtonOutline(
                modifier = Modifier.padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                text = "Aceptar",
                textColor = Color.White,
                isLoading = false,
                onClick = {
                    onAction(AddLeadAction.OnBackClick)
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}