@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

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
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.auth.presentation.login.components.ForgotPasswordDialog
import org.propapel.prospeccion.auth.presentation.login.components.LoginDesktopScreen
import org.propapel.prospeccion.auth.presentation.login.components.mobile.LoginMobileScreen
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.core.presentation.designsystem.components.ProspeccionTextFieldAnimation
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo


@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel,
    onLoginSuccess: (AuthInfo) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.loginSuccess) {
        delay(2000)
        if (state.loginSuccess) {
            onLoginSuccess(state.user)
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
    val windowSizeClass = calculateWindowSizeClass()

    LaunchedEffect(state.isError) {
        if (state.isError) {
            delay(2000) // Espera de 2 segundos
            onAction(LoginAction.HideError)
        }
    }

    var toggleDialogForgotPassword by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        )
    ) {
        if(calculateWindowSizeClass().isMobile){
           LoginMobileScreen(
               state = state,
               onAction = onAction
           )
        }else{
            LoginDesktopScreen(
                state = state,
                onAction = onAction
            )
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

