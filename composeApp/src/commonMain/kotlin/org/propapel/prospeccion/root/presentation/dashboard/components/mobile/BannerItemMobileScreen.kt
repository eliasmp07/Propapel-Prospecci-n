package org.propapel.prospeccion.root.presentation.dashboard.components.mobile

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun BannerItemMobileScreen(
    modifier: Modifier = Modifier.aspectRatio(16f / 7f),
    onClickBanner : (String) -> Unit,
    banner: Banner,
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onClickBanner(banner.url)
        }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = banner.imageUrl,
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
    }
}

@Composable
fun BannerItemDesktopScreen(
    modifier: Modifier = Modifier.aspectRatio(16f / 7f),
    onClickBanner : (String) -> Unit,
    banner: Banner,
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onClickBanner(banner.url)
        }
    ) {
        AsyncImage(
            model = banner.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            onError = { error -> print("ImageLoadError Error loading image: ${error.result.throwable}") },
            onLoading = { print("ImageLoad Loading image: ${banner.imageUrl}") }
        )
    }
}