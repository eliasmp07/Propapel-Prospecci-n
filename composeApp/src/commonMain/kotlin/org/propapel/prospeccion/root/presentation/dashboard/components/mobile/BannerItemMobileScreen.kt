package org.propapel.prospeccion.root.presentation.dashboard.components.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.banner_prueba

@Composable
fun BannerItemMobileScreen(
    banner: Banner,
) {
    ElevatedCard(
        modifier = Modifier.aspectRatio(16f / 7f),
        shape = RoundedCornerShape(20.dp),
        onClick = {
        }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.banner_prueba),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
    }
}