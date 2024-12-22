package org.propapel.prospeccion.auth.presentation.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.auth.presentation.login.LoginAction
import org.propapel.prospeccion.auth.presentation.login.LoginState
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProspeccionTextFieldAnimation
import org.propapel.prospeccion.root.presentation.addlead.GenericContentWindowsSize
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo
import prospeccion.composeapp.generated.resources.merida
import prospeccion.composeapp.generated.resources.mexico
import prospeccion.composeapp.generated.resources.monterrey

@Composable
fun LoginDesktopScreen(
    state: LoginState,
    onAction: (LoginAction)  -> Unit
) {
    val items = listOf(
        Res.drawable.merida,
        Res.drawable.mexico,
        Res.drawable.monterrey
    )

    val pagerState = rememberPagerState(pageCount = { items.size })

    // Auto-desplazamiento del pager
    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000) // Cambia de página cada minuto
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % items.size)
        }
    }

    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxSize()
        ){
            Row {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(0.5f).fillMaxHeight().padding(60.dp).clip(RoundedCornerShape(30.dp))
                ){page ->
                    val image  = items[page]
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(image),
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.weight(0.5f).fillMaxHeight().padding(100.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "Inicio de sessión", style = MaterialTheme.typography.headlineMedium,
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
                        shape = RoundedCornerShape(12.dp),
                        isLoading = state.isLogging,
                        onClick = {
                            focusManager.clearFocus()
                            onAction(LoginAction.OnLoginClick)
                        }
                    )
                }
            }
        }
    }
}