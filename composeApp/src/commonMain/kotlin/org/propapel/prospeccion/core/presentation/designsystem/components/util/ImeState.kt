package org.propapel.prospeccion.core.presentation.designsystem.components.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
expect fun rememberImeState(): State<Boolean>