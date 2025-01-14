package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.runtime.Composable

@Composable
actual fun DatePickerDialog(
    onDateSelected: (Long?, String) -> Unit,
    onDismiss: () -> Unit
) {
}