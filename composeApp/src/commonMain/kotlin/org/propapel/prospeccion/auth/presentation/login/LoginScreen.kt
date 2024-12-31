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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.auth.presentation.login.components.desktop.LoginDesktopScreen
import org.propapel.prospeccion.auth.presentation.login.components.mobile.LoginMobileScreen
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person


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

    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        )
    ) {
        if(windowSizeClass.isMobile){
            Image(
                modifier = Modifier.size(200.dp).align(Alignment.BottomEnd),
                painter = painterResource(Res.drawable.customer_person),
                contentDescription = null
            )
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

