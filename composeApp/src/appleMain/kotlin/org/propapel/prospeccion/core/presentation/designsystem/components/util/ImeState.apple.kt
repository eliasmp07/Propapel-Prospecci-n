package org.propapel.prospeccion.core.presentation.designsystem.components.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
actual fun rememberImeState(): State<Boolean> {
    val imeState = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val keyboardWillShow = NSNotificationCenter.defaultCenter.addObserverForName(
            name = UIKeyboardWillShowNotification,
            `object` = null,
            queue = null
        ) { _ ->
            imeState.value = true
        }

        val keyboardWillHide = NSNotificationCenter.defaultCenter.addObserverForName(
            name = UIKeyboardWillHideNotification,
            `object` = null,
            queue = null
        ) { _ ->
            imeState.value = false
        }

        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(keyboardWillShow)
            NSNotificationCenter.defaultCenter.removeObserver(keyboardWillHide)
        }
    }

    return imeState
}