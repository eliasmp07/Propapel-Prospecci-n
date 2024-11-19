package org.propapel.prospeccion.core.presentation.designsystem.components.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue


fun Modifier.carouselTransition(
    page: Int,
    pagerState: PagerState,
): Modifier {
    val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
    val scale = if (pageOffset < 0.5f) 1f else lerp(
        start = 0.85f, // Escala mínima para páginas alejadas.
        stop = 1f,     // Escala máxima para la página central.
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
    val alpha = lerp(
        start = 0.5f,  // Opacidad mínima.
        stop = 1f,     // Opacidad máxima.
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
        this.alpha = alpha
    }
}



/**
 *Animacion para hacer que un elemeno se mueva de un punto A a un punto B y se repita
 */
@Composable
fun Modifier.animateOffset(): Modifier {
    val animation = rememberInfiniteTransition(label = "")
    val offsetIcon by animation.animateValue(
        typeConverter = Dp.VectorConverter,
        initialValue = (-5).dp,
        targetValue = 25.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    return this.offset(x = offsetIcon)
}

/**
 * Animacion para hacer que un elemento entre de lado derecho y llegue a su posicion
 *
 * @param targetValue Valor donde llegara el elemento
 * @param durationMillis Duracion de la animacion
 *
 * @return retorna el modifier con el offset que hara la animacion de moverse
 */
@Composable
fun Modifier.animateEnterTop(
    targetValue: Float = 0f,
    durationMillis: Int = 1500,
): Modifier {
    val animatable = remember { Animatable(-500f) }

    LaunchedEffect(animatable) {
        animatable.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = EaseOut
            )
        )
    }

    return this
        .offset {
            IntOffset(
                0,
                animatable.value.toInt()
            )
        }
}

/**
 * Animacion para hacer que un elemento entre de lado derecho y llegue a su posicion
 *
 * @param targetValue Valor donde llegara el elemento
 * @param durationMillis Duracion de la animacion
 *
 * @return retorna el modifier con el offset que hara la animacion de moverse
 */
@Composable
fun Modifier.animateEnterRight(
    targetValue: Float = 0f,
    durationMillis: Int = 1500,
): Modifier {
    val animatable = remember { Animatable(1000f) }

    LaunchedEffect(animatable) {
        animatable.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = EaseOut
            )
        )
    }

    return this
        .offset {
            IntOffset(
                animatable.value.toInt(),
                0
            )
        }
}

/**
 * Animacion para hacer que un elemento entre desde abajo de la pantalla y llegue a su posicion
 *
 * @param targetValue Valor donde llegara el elemento
 * @param durationMillis Duracion de la animacion
 * @param initialOffsetY Valor inicia desde donde iniciar la animacion
 *
 * @return retorna el modifier con el offset que hara la animacion de moverse
 */
@Composable
fun Modifier.animateEnterBottom(
    initialOffsetY: Float = 500f,
    targetValue: Float = 0f,
    durationMillis: Int = 1500,
): Modifier {
    val animatable = remember { Animatable(initialOffsetY) }

    LaunchedEffect(animatable) {
        animatable.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = durationMillis
            )
        )
    }

    return this
        .offset {
            IntOffset(
                0,
                animatable.value.toInt()
            )
        }
}


/**
 * Animacion para hacer que un elemento entre de lado izquierdo de la pantalla y llegue a su posicion
 *
 * @param targetValue Valor donde llegara el elemento
 * @param durationMillis Duracion de la animacion
 * @param initialOffsetX Valor inicia desde donde iniciar la animacion
 *
 * @return retorna el modifier con el offset que hara la animacion de moverse
 */
@Composable
fun Modifier.animateEnterFromLeft(
    initialOffsetX: Float = -1000f,
    targetValue: Float = 0f,
    durationMillis: Int = 1500,
): Modifier {
    val animatable = remember { Animatable(initialOffsetX) }

    LaunchedEffect(animatable) {
        animatable.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = EaseOut
            )
        )
    }

    return this
        .offset {
            IntOffset(
                animatable.value.toInt(),
                0
            )
        }
}

/**
 * Animacion para hacer que un elemento aparesca y haga un rebote al llegar a su destino
 *
 * @param targetValue Valor donde llegara el elemento
 * @param durationMillis Duracion de la animacion
 * @param initialValue inicia desde donde iniciar la animacion
 *
 * @return retorna el modifier con el graphicsLayer con el escalado animado
 */
@Composable
fun Modifier.animateAttention(
    targetValue: Float = 1f,
    initialValue: Float = 0f,
    durationMillis: Int = 1500
): Modifier {
    val animatable = remember { Animatable(initialValue) }
    LaunchedEffect(Unit) {
        animatable.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = EaseOutBounce
            )
        )
    }
    return this.graphicsLayer {
        scaleX = animatable.value
        scaleY = animatable.value
    }
}

@Composable
fun Modifier.animateAttentionRepeat(
    targetValue: Float = 1f,
    initialValue: Float = 0.7f,
    durationMillis: Int = 2500
): Modifier {
    val animatable = remember { Animatable(initialValue) }
    LaunchedEffect(Unit) {
        while (true) { // Bucle infinito para repetir la animación
            animatable.animateTo(
                targetValue = targetValue,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis,
                        easing = EaseOutBounce
                    ),
                    repeatMode = RepeatMode.Reverse,
                )
            )
            animatable.snapTo(initialValue) // Reinicia el valor para repetir la animación
        }
    }
    return this.graphicsLayer {
        scaleX = animatable.value
        scaleY = animatable.value
    }
}
