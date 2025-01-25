package org.propapel.prospeccion.root.presentation.updateCustomer.components.desktop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.GenericContentWindowsSize
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ExpisedDropdownMenuTypeClient
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.createProject.componetns.ExposedDropdownMenuGereric
import org.propapel.prospeccion.root.presentation.updateCustomer.UpdateLeadAction
import org.propapel.prospeccion.root.presentation.updateCustomer.UpdateLeadState

@Composable
fun UpdateCustomerDesktopScreen(
    state: UpdateLeadState,
    onAction: (UpdateLeadAction) -> Unit
) {
    LaunchedEffect(state.isErrorUpdateLead) {
        if (state.isErrorUpdateLead) {
            delay(2000) // Espera de 2 segundos
            onAction(UpdateLeadAction.HideError)
        }
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            delay(2000) // Espera de 2 segundos
            onAction(UpdateLeadAction.HideSuccess)
        }
    }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GenericContentWindowsSize(
            content1 = {
                KottieAnimationUtil(
                    modifier = Modifier.weight(0.5f).aspectRatio(3f),
                    fileRoute = "files/anim_customer.json"
                )
            },
            colorIcon = Color.Black,
            brush = Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            ),
            onCloseScreen = {
                onAction(UpdateLeadAction.OnBackClick)
            },
            content2 = {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Actualizar informacion del cliente",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                var expandedTypeOfClient by remember {
                    mutableStateOf(false)
                }
                ExposedDropdownMenuGereric(
                    state = expandedTypeOfClient,
                    modifier = Modifier,
                    title = "Tipo de cliente",
                    listOptions = TypeOfClient.getTypeOfClient(),
                    onDimiss = {
                        expandedTypeOfClient = !expandedTypeOfClient
                    },
                    optionSelectable = state.typeOfClient,
                    content = {
                        DropdownMenuItem(
                            text = { androidx.compose.material.Text(text = it) },
                            onClick = {
                                expandedTypeOfClient = !expandedTypeOfClient
                                onAction(UpdateLeadAction.OnTypeOfClientChange(it))
                            }
                        )
                    },
                )
                ProSalesTextField(
                    title = "Razon social",
                    state = state.nameCompany,
                    colors = Color.Black,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    onTextChange = {
                        onAction(UpdateLeadAction.OnNameCompanyChange(it))
                    },
                    startIcon = Icons.Filled.Business
                )
                ProSalesTextField(
                    title = "Nombre completo",
                    state = state.name,
                    colors = Color.Black,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    onTextChange = {
                        onAction(UpdateLeadAction.OnUpdateNameChange(it))
                    },
                    startIcon = Icons.Filled.Person
                )
                ProSalesTextField(
                    title = "Correo electronico",
                    colors = Color.Black,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    state = state.email,
                    onTextChange = {
                        onAction(UpdateLeadAction.OnUpdateEmailChange(it))
                    },
                    startIcon = Icons.Filled.Email
                )
                ProSalesTextField(
                    title = "Numero de telefono",
                    colors = Color.Black,
                    state = state.phone,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    onTextChange = {
                        onAction(UpdateLeadAction.OnUpdatePhoneChange(it))
                    },
                    startIcon = Icons.Filled.Phone
                )
                ProSalesTextField(
                    title = "Direccion",
                    colors = Color.Black,
                    state = state.address,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    onTextChange = {
                        onAction(UpdateLeadAction.OnUpdateAddress(it))
                    },
                    startIcon = Icons.Filled.Map
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                ProSalesActionButtonOutline(
                    text = "Actualizar",
                    isLoading = state.isUpdatingLead,
                    onClick = {
                        focusManager.clearFocus()
                        onAction(UpdateLeadAction.UpdateLeadClick)
                    }
                )
            }
        )
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = state.isErrorUpdateLead,
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
                        text = state.errorUpdateLead?.asString()?:"",
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