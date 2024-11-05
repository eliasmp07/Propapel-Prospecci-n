package org.propapel.prospeccion.root.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.account.components.ImageProfile

@Composable
fun AccountScreenRoot(
    viewModel: AccountSMViewModel,
    onAction: (AccountSMAction) -> Unit
){
    val state by viewModel.state.collectAsState()
    AccountScreen(
        state = state,
        onAction = onAction
    )
}

@Composable
private fun AccountScreen(
    state: AccountSMState,
    onAction: (AccountSMAction) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        )
    ) {
        ImageProfile(
            profileImg = state.user.image,
            isAdmin = state.user.isAdmin
        )
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = state.user.name + " " +state.user.lastname, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Ventas", style = MaterialTheme.typography.titleMedium)
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
               if(state.user.isAdmin){
                   ProSalesActionButton(
                       modifier = Modifier.weight(1f),
                       text = "Cambiar de sucursal",
                       onClick = {
                           onAction(AccountSMAction.OnSelectSucursal)
                       },
                   )
                   Spacer(
                       modifier = Modifier.width(8.dp)
                   )
               }
               ProSalesActionButtonOutline(
                    modifier = Modifier.weight(1f),
                    text = "Editar perfil",
                    onClick = {
                        onAction(AccountSMAction.EditProfileClick)
                    }
                )
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            ProSalesActionButtonOutline(
                text = "Cerrar sessión",
                onClick = {
                    onAction(AccountSMAction.OnLogoutClick)
                }
            )
        }
    }
}