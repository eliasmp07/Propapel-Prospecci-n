package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateLogo
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo

@Composable
fun LoadingPropapel(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0f to PrimaryYellowLight,
                    0.6f to SoporteSaiBlue30,
                    1f to MaterialTheme.colorScheme.primary
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier.animateLogo(),
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo de la app",
            alpha = 0.5f
        )
    }
}