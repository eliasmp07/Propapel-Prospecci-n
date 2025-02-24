@file:OptIn(ExperimentalPermissionsApi::class)

package org.propapel.prospeccion.notifications.utils
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.ui.permissions.Permission

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeAskPermission(
    permission: String,
    modifier: Modifier = Modifier
) {
    val permissionState = rememberPermissionState(permission = permission)
    LaunchedEffect(key1 = Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.shouldShowRationale) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = { /*TODO*/ },
            buttons = {
                ProSalesActionButton(
                    text = "Aceptar",
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    permissionState.launchPermissionRequest()
                }
            },
            title = {
                Text(text = "Permiso requerido")
            },
            text = {
                Text(text = "Nosotros necesitamos le permiso de notifiaciones para poder mandarle alertas de tus citas")
            }
        )
    }
}

@Composable
fun MultiPermission(
    permissions: List<String>,
    modifier: Modifier = Modifier
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = permissions
    )

    // Solicitar permisos al iniciar el Composable
    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }

    // Mostrar un AlertDialog si se necesita racionalizar algún permiso
    val shouldShowRationale = permissionsState.permissions.any { it.status.shouldShowRationale }

    if (shouldShowRationale) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = { /* Manejar el caso de dismiss */ },
            buttons = {
                ProSalesActionButton(
                    text = "Aceptar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    permissionsState.launchMultiplePermissionRequest()
                }
            },
            title = {
                Text(text = "Permiso requerido")
            },
            text = {
                Text(text = "Nosotros necesitamos el permiso.")
            }
        )
    }
}
