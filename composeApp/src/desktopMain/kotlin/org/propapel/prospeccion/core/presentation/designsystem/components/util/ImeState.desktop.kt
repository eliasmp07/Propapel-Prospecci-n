package org.propapel.prospeccion.core.presentation.designsystem.components.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalFocusManager
import org.propapel.prospeccion.core.presentation.ui.extensions.isNull

@Composable
actual fun rememberImeState(): State<Boolean> {
    val imeState = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    LaunchedEffect(Unit) {
        snapshotFlow { focusManager }
            .collect { isFocused ->
                imeState.value = !isFocused.isNull()
            }
    }

    return imeState
}