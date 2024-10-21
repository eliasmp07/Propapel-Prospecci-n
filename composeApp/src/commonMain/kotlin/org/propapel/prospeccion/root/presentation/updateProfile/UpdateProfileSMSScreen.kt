@file:OptIn(ExperimentalMaterial3Api::class,
            ExperimentalMaterial3Api::class
)

package org.propapel.prospeccion.root.presentation.updateProfile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.propapel.prospeccion.auth.presentation.login.LoginAction
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.presentation.updateProfile.components.PhotoProfileUpdate


@Composable
fun UpdateProfileSMSScreenRoot(
    viewModel: UpdateProfileSMViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    UpdateProfileSMSScreen(
        state = state,
        onAction = {action ->
            when(action){
                UpdateProfileSMAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun UpdateProfileSMSScreen(
    state: UpdateProfileSMState,
    onAction :(UpdateProfileSMAction) -> Unit
) {

    val scope = rememberCoroutineScope()
    val context = com.mohamedrejeb.calf.core.LocalPlatformContext.current
    var byteArray by remember { mutableStateOf(ByteArray(0)) }

    LaunchedEffect(state.isError) {
        if (state.isError) {
            delay(2000) // Espera de 2 segundos
            onAction(UpdateProfileSMAction.HideError)
        }
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            delay(2000) // Espera de 2 segundos
            onAction(UpdateProfileSMAction.HideSuccess)
        }
    }


    val filePicker = com.mohamedrejeb.calf.picker.rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = {
            files ->
            scope.launch {
                files.firstOrNull()?.let {
                    byteArray = it.readByteArray(context = context)
                    onAction(UpdateProfileSMAction.OnChangeImageProfile(byteArrayToBase64(it.readByteArray(context = context))))
                }
            }
        }
    )
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onAction(UpdateProfileSMAction.OnBackClick)
                        },
                        content = {
                            Icon(
                                Icons.Default.ArrowBackIosNew,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    )
                },
                title = {

                }
            )
        }
    ) {innerPadding ->
        Box(
            Modifier.padding(innerPadding)
        ){
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                PhotoProfileUpdate(
                    profile = state.user.image,
                    image = byteArray,
                    onClick = {
                        filePicker.launch()
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ProSalesTextField(
                    title = "Nombre",
                    state = state.user.name,
                    onTextChange = {

                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ProSalesTextField(
                    title = "Apellidos",
                    state = state.user.lastname,
                    onTextChange = {

                    }
                )
                /*
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ProSalesTextField(
                    title = "Numero de telefono",
                    state = state.user.,
                    onTextChange = {

                    }
                )*/
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                ProSalesActionButton(
                    text = "Actualizar perfil",
                    onClick = {
                        onAction(UpdateProfileSMAction.OnUpdateClick)
                    }
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
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
                            text = state.error,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = state.isSuccess,
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
                        containerColor = Color(0xFF46D19E)
                    )
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                    ){
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = state.success,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
    if (state.isLoading){
        LoadingPropapel(
            modifier = Modifier.fillMaxSize()
        )
    }
}
fun byteArrayToBase64(byteArray: ByteArray): String {
    val base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    val output = StringBuilder()

    var i = 0
    while (i < byteArray.size) {
        val byte1 = byteArray[i].toInt() and 0xFF
        val byte2 = if (i + 1 < byteArray.size) byteArray[i + 1].toInt() and 0xFF else -1
        val byte3 = if (i + 2 < byteArray.size) byteArray[i + 2].toInt() and 0xFF else -1

        val out1 = byte1 shr 2
        val out2 = ((byte1 and 0x03) shl 4) or (byte2 shr 4)
        val out3 = ((byte2 and 0x0F) shl 2) or (byte3 shr 6)
        val out4 = byte3 and 0x3F

        output.append(base64Chars[out1])
        output.append(if (byte2 != -1) base64Chars[out2] else '=')
        output.append(if (byte3 != -1) base64Chars[out3] else '=')
        output.append(if (byte3 != -1) base64Chars[out4] else '=')

        i += 3
    }

    return output.toString()
}

