package org.propapel.prospeccion.root.presentation.leads

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.ui.UiText
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.no_internet

@Composable
fun <T> GenericContentLoading(
    modifier: Modifier = Modifier,
    data: State<T>,
    retry: () -> Unit,
    success: @Composable (T) -> Unit
) {

    AnimatedContent(
        data,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(3000)
            ) togetherWith fadeOut(animationSpec = tween(3000))
        },
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            when (data) {
                is State.Error -> retry()
                is State.Loading -> retry()
                else -> Unit
            }
        },
        label = "Animated Content"
    ) { targetState ->
        when (targetState) {
            is State.Loading -> {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                         16.dp
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9)),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is State.Empty -> {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            16.dp
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9)),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.no_internet),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Contenido vacio"
                        )
                    }
                }
            }
            is State.Error -> {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f4f9)),
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = targetState.message?.asString() ?: "Hubo un error"
                        )
                    }
                }
            }
            is State.Success -> success(targetState.value)
        }
    }
}

sealed class State<T> {
    class Loading<T> : State<T>()
    class Empty<T> : State<T>()
    data class Success<T>(val value: T) : State<T>()
    data class Error<T>(val message: UiText? = null) : State<T>()
}

