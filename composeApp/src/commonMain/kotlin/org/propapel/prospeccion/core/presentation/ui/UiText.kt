package org.propapel.prospeccion.core.presentation.ui


import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    data class DynamicString(val value: String): UiText
    class StringResource(
        val id: org.jetbrains.compose.resources.StringResource,
        val args: Array<Any> = arrayOf()
    ): UiText

    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> stringResource(resource = id, *args)
        }
    }

}