package org.propapel.prospeccion.auth.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField

@Composable
fun ForgotPasswordDialog(
    modifier: Modifier = Modifier,
    email: String,
    onDismissRequest: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            ElevatedCard(
                modifier = modifier
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            onDismissRequest()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Recuperar contraseña",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(text = "Por favor ingresa tu correo para poder cambiar tu contraseña")
                    Spacer(Modifier.height(8.dp))
                    ProSalesTextField(
                        title = "Correo electronico",
                        startIcon = Icons.Default.Email,
                        state = email,
                        hint = "example@propapel.com.mx",
                        onTextChange = onValueChange
                    )
                    Spacer(Modifier.height(8.dp))
                    ProSalesActionButton(
                        text = "Recuperar",
                        isLoading = false,
                        onClick = {

                        }
                    )
                }
            }
        }
    )
}