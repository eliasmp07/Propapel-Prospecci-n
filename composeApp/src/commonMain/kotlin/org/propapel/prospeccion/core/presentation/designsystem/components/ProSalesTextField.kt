package org.propapel.prospeccion.core.presentation.designsystem.components

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.sharp.Visibility
import androidx.compose.material.icons.sharp.VisibilityOff
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiDarkRed


/**
 * Campo de texto reutilizable.
 *
 * @param modifier Modificador para aplicar a este Composable.
 * @param state Texto actual del campo de texto.
 * @param startIcon Icono opcional para mostrar al inicio del campo de texto.
 * @param endIcon Icono opcional para mostrar al final del campo de texto.
 * @param onTextChange Callback que se llama cuando cambia el texto.
 * @param hint Texto de sugerencia cuando el campo está vacío.
 * @param title Título opcional para mostrar encima del campo de texto.
 * @param isClickableText Callback opcional que se llama cuando se hace clic en el texto adicional.
 * @param enabled Booleano para indicar si el campo de texto está habilitado.
 * @param keyboardOptions Opciones del teclado.
 * @param keyboardActions Acciones del teclado.
 * @param error Mensaje de error opcional para mostrar debajo del campo de texto.
 * @param isPassword Booleano para indicar si el campo de texto es una contraseña.
 * @param additionalInfo Información adicional opcional para mostrar debajo del campo de texto.
 * @param maxLines Número máximo de líneas del campo de texto.
 *
 * @author Elias Mena
 */
@Composable
fun ProSalesTextField(
    modifier: Modifier = Modifier,
    state: String,
    colors: Color = Color.Black,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    onTextChange: (String) -> Unit,
    hint: String = "",
    readOnly: Boolean = false,
    title: String? = null,
    isClickableText: () -> Unit = {},
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    error: String? = null,
    modifierTextField: Modifier =Modifier,
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
                .clip(RoundedCornerShape(30.dp))
                .background(Color.Black.copy(alpha = 0.05f))
                .border(
                    width = 1.dp,
                    color = colors,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(12.dp)
                .onFocusChanged { isFocused = it.isFocused },
            onValueChange = { onTextChangeState(it) },
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
                        if (state.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = colors,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerBox()
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (error != null) {
                FormError()
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = error,
                    color = colors,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (additionalInfo != null) {
                Text(
                    modifier = Modifier.clickable { isClickableText() },
                    text = additionalInfo,
                    color = colors,
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

@Preview
@Composable
private fun DolarBlueTextFieldPreview(

) {
    Column {
            var email by remember {
                mutableStateOf("")
            }
            ProSalesTextField(
                state = email,
                startIcon = Icons.Filled
                    .Email,
                error = null,
                additionalInfo = null,
                endIcon = null,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {

                    }
                ),
                title = "Email",
                onTextChange = {
                    email = it
                })

    }
}