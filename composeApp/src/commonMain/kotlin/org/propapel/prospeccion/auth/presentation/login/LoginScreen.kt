package org.propapel.prospeccion.auth.presentation.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.auth.presentation.login.components.ForgotPasswordDialog
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo


@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.loginSuccess) {
        if (state.loginSuccess) {
            onLoginSuccess()
        }
    }

    LoginScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    LaunchedEffect(state.isError) {
        if (state.isError) {
            delay(2000) // Espera de 2 segundos
            onAction(LoginAction.HideError)
        }
    }

    var toggleDialogForgotPassword by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(
                MaterialTheme.colorScheme.primary
            )
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
                        text = "Inicio de sessión", style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Ingresa tus credenciales para continuar en nuestra App",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(16.dp))
                    ProSalesTextField(
                        title = "Correo electronico",
                        startIcon = Icons.Default.Email,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        error = state.emailError,
                        state = state.email,
                        hint = "example@propapel.com.mx",
                        onTextChange = {
                            onAction(LoginAction.OnEmailChange(it))
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    ProSalesTextField(
                        title = "Contraseña",
                        state = state.password,
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
                        isClickableText = {
                            toggleDialogForgotPassword = !toggleDialogForgotPassword
                        },
                        additionalInfo = "¿Olvidastes tu contraseña?"
                    )
                    Spacer(Modifier.height(16.dp))
                    ProSalesActionButton(
                        text = "Iniciar sessión",
                        isLoading = state.isLogging,
                        onClick = {
                            onAction(LoginAction.OnLoginClick)
                        }
                    )
                }
            }
            Spacer(
                modifier = Modifier.weight(0.5f)
            )
            if (toggleDialogForgotPassword) {
                ForgotPasswordDialog(
                    modifier = Modifier,
                    email = "",
                    onDismissRequest = {
                        toggleDialogForgotPassword = !toggleDialogForgotPassword
                    },
                    onValueChange = {

                    }
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = state.isError,
            enter = slideInVertically(
                initialOffsetY = { it } // Aparece de abajo hacia arriba
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it } // Desaparece de arriba hacia abajo
            ) + fadeOut(),
        ) {
            ElevatedCard(
                modifier = Modifier.padding(10.dp).align(Alignment.BottomCenter),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                ){
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null
                    )
                    Text(
                        text = state.error.asString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

