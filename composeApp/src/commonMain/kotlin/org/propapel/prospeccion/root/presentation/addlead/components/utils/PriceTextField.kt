package org.propapel.prospeccion.root.presentation.addlead.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Visibility
import androidx.compose.material.icons.sharp.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiDarkRed

@Composable
fun ProSalesPriceTextField(
    modifier: Modifier = Modifier,
    state: String,
    colors: Color = Color.Black,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    onTextChange: (String) -> Unit,
    roundedCornerShape: Dp = 30.dp,
    hint: String = "",
    readOnly: Boolean = false,
    title: String? = null,
    isClickableText: () -> Unit = {},
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
    error: String? = null,
    modifierTextField: Modifier = Modifier,
    isPassword: Boolean = false,
    additionalInfo: String? = null,
    maxLines: Int = 1,
) {
    var hidePassword by remember { mutableStateOf(true) }
    var isFocused by remember { mutableStateOf(false) }
    val onTextChangeState by rememberUpdatedState(onTextChange)

    Column(modifier = modifier) {
        if (title != null) {
            Text(
                text = title,
                color = colors,
                fontWeight = FontWeight.Bold
            )
        }

        BasicTextField(
            value = state,
            enabled = enabled,
            textStyle = TextStyle(color = colors),
            readOnly = readOnly,
            cursorBrush = SolidColor(colors),
            visualTransformation = if (isPassword && hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier
                .clip(RoundedCornerShape(roundedCornerShape))
                .background(Color.Black.copy(alpha = 0.05f))
                .border(
                    width = 1.dp,
                    color = colors,
                    shape = RoundedCornerShape(roundedCornerShape)
                )
                .padding(12.dp)
                .onFocusChanged { isFocused = it.isFocused },
            onValueChange = { input ->
                // Validar que solo acepte dígitos y decimales
                val filteredInput = input.filter { it.isDigit() || it == '.' }
                onTextChangeState(filteredInput)
            },
            decorationBox = { innerBox ->
                Row(
                    modifier = modifierTextField.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (startIcon != null) {
                        Icon(
                            imageVector = startIcon,
                            contentDescription = null,
                            tint = colors
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        // Mostrar el símbolo $ solo como decoración
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$", // Este símbolo es decorativo
                                color = colors
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            innerBox()
                        }
                        if (state.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = colors,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    if (endIcon != null) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = endIcon,
                            tint = colors,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    if (isPassword) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = if (hidePassword) Icons.Sharp.Visibility else Icons.Sharp.VisibilityOff,
                            contentDescription = null,
                            tint = colors,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { hidePassword = !hidePassword }
                        )
                    }
                }
            }
        )

        // Error y adicional información
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (error != null) {
                FormError()
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = error,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (additionalInfo != null) {
                Text(
                    modifier = Modifier.clickable { isClickableText() },
                    text = additionalInfo,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun FormError() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(SoporteSaiDarkRed)
            .size(10.dp)
    )
}
