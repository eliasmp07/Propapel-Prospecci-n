@file:OptIn(ExperimentalResourceApi::class)

package org.propapel.prospeccion.root.presentation.addlead.components.utils

import KottieAnimation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import prospeccion.composeapp.generated.resources.Res

@Composable
fun KottieAnimationUtil(
    modifier: Modifier = Modifier,
    fileRoute: String = "",
    interations: Int = 100
){
    var animation by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animation = Res.readBytes(fileRoute)
            .decodeToString()
    }
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation) // Or KottieCompositionSpec.Url || KottieCompositionSpec.JsonString
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        reverseOnRepeat = true,
        iterations = interations,

    )
    KottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = modifier
    )
}