package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
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
            .background(color = MaterialTheme.colorScheme.background.copy(0.8f)),
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