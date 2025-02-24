package org.propapel.prospeccion.auth.presentation.login.components.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.auth.presentation.login.LoginAction
import org.propapel.prospeccion.auth.presentation.login.LoginState
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProspeccionTextFieldAnimation
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.logo

@Composable
fun LoginMobileScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box {
        Image(
            modifier = Modifier.size(150.dp).align(Alignment.BottomEnd),
            painter = painterResource(Res.drawable.customer_person),
            contentDescription = null
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier.weight(0.2f)
            )
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            )
            Spacer(
                modifier = Modifier.weight(0.2f)
            )
            ElevatedCard(
                modifier = Modifier.width(500.dp).padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                elevation = CardDefaults.elevatedCardElevation(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Inicio de sessión",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Ingresa tus credenciales para continuar en nuestra App",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(16.dp))
                    ProspeccionTextFieldAnimation(
                        hint = "Correo electronico",
                        startIcon = Icons.Default.Email,
                        error = state.emailError,
                        text = state.email,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email
                        ),
                        onTextChange = {
                            onAction(LoginAction.OnEmailChange(it))
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    ProspeccionTextFieldAnimation(
                        text = state.password,
                        hint = "Contraseña",
                        isPassword = true,
                        startIcon = Icons.Default.Lock,
                        error = state.passwordError,
                        onTextChange = {
                            onAction(LoginAction.OnPasswordChange(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                    )
                    Spacer(Modifier.height(16.dp))
                    ProSalesActionButton(
                        text = "Iniciar sesión",
                        isLoading = state.isLogging,
                        onClick = {
                            focusManager.clearFocus()
                            onAction(LoginAction.OnLoginClick)
                        }
                    )
                }
            }
            Spacer(
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}