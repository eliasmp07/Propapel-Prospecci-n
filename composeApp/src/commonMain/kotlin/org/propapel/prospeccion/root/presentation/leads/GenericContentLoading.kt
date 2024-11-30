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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.ui.UiText
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.empty_info
import prospeccion.composeapp.generated.resources.no_internet

/**
 * Contenido para cargar datos la ui
 *
 * @param modifier Modificador del composable
 * @param data Informacion que se va a validar
 * @param contentLoading Contenido que se mostrara cuando se carga la informacion
 * @param contentError Contenido que se mostrara cuando marque error la informacion a cargar
 * @param contentEmpty Contenido que se mostrara cuando el contenido a cargar este vacio
 * @param retry Lambda que se llama para cuando falla y se requiere cargar de nuevo la info
 * @param textContentEmpty Texto que se mostrara cuando este vacio el contenido
 * @param success Contenido que se mostrara cuando la carga de los datos sea exitosa
 *
 * @author Elias Mena Pech
 */
@Composable
fun <T> GenericContentLoading(
    modifier: Modifier = Modifier,
    data: UiState<T>,
    contentLoading: @Composable ((Modifier) -> Unit)? = { GenericContentLoading.GenericLoadingContent(modifier = it) },
    contentError: @Composable ((error: Pair<UiText, DrawableResource>) -> Unit)? = { GenericContentLoading.ErrorContentGeneric(error = it) },
    contentEmpty: @Composable ((Modifier, String) -> Unit)? = { modifier1, text ->
        GenericContentLoading.GenericEmptyContent(modifier = modifier1, text = text ) },
    retry: () -> Unit,
    textContentEmpty: String = "No tienes info cargada",
    success: @Composable (T) -> Unit
) {
    AnimatedContent(
        data,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(3000)
            ) togetherWith fadeOut(animationSpec = tween(3000))
        },
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            when (data) {
                is UiState.Error -> retry()
                is UiState.Loading -> retry()
                else -> Unit
            }
        },
        label = "Animated Content"
    ) { targetState ->
        when (targetState) {
            is UiState.Loading -> {
                if (contentLoading != null) {
                    contentLoading(modifier)
                }
            }
            is UiState.Empty -> {
                if (contentEmpty != null) {
                    contentEmpty(modifier, textContentEmpty)
                }
            }
            is UiState.Error -> {
                if (contentError != null) {
                    contentError(
                        targetState.error ?: Pair(
                            UiText.DynamicString("Error"),
                            Res.drawable.no_internet
                        )
                    )
                }
            }
            is UiState.Success -> success(
                targetState.value
            )
        }
    }
}


object GenericContentLoading {

    /**
     * Contenido generico cuando la informacion este cargando
     */
    @Composable
    fun GenericLoadingContent(
        modifier: Modifier = Modifier
    ) {
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

    /**
     * Contenido generico cuando la informacion cargada este vacia
     */
    @Composable
    fun GenericEmptyContent(
        modifier: Modifier = Modifier,
        text: String = ""
    ) {
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
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(Res.drawable.empty_info),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = text,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }

    /**
     * Contenido generico cuando la informacion marque error
     */
    @Composable
    fun ErrorContentGeneric(
        modifier: Modifier = Modifier,
        error: Pair<UiText, DrawableResource>
    ) {
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
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(error.second),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = error.first.asString(),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Clase Sellada que valida los estados de la UI
 *
 * @author Elias Mena Pech
 */
sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    class Empty<T> : UiState<T>()
    data class Success<T>(val value: T) : UiState<T>()
    data class Error<T>(val error: Pair<UiText, DrawableResource>? = null) : UiState<T>()
}

/**
 * Funcion de extension que valida la info cuando sea vacio
 *
 * @author Elias Mena
 */
inline fun <T> List<T>.toState(): UiState<List<T>> {
    return when {
        isEmpty() -> UiState.Empty()
        else -> UiState.Success(this)
    }
}