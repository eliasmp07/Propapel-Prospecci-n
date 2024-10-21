package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import org.propapel.prospeccion.core.presentation.ui.UiText

/**
 * Muestra diferentes composables según el estado de la vista.
 *
 * @param isLoading Indica si la vista está en estado de carga.
 * @param contentLoading Composable que se muestra cuando "isLoading" es verdadero.
 * @param isEmpty Indica si la respuesta está vacía. Se usa, por ejemplo, para listas vacías.
 * @param errorContent Composable que se muestra cuando hay un error.
 * @param contentEmpty Composable que se muestra cuando "isEmpty" es verdadero. Opcional, por defecto no hace nada.
 * @param error Error recibido desde el backend. Si no es nulo, se mostrará "errorContent".
 * @return Devuelve `false` si se está mostrando alguna de las vistas de estado (cargando, vacío, error).
 * Devuelve "true" solo si no hay errores, la lista no está vacía y no está en estado de carga, lo que indica que se puede mostrar la vista principal.
 */
@Composable
fun handleResultView(
    isLoading: Boolean,
    contentLoading:  @Composable () -> Unit,
    isEmpty: Boolean = false,
    errorContent:  @Composable (UiText) -> Unit,
    contentEmpty: @Composable () -> Unit = {},
    error: UiText?,
): Boolean {
    return when {
        isLoading -> {
            contentLoading()
            false
        }
        error != null -> {
            errorContent(error)
            false
        }
        isEmpty -> {
            contentEmpty()
            false
        }
        else -> true
    }
}