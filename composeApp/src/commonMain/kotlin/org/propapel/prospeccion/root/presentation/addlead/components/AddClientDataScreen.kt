@file:OptIn(ExperimentalResourceApi::class)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryGreen
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryViolet
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ExpisedDropdownMenuTypeClient
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil

@Composable
fun AddClientDataScreen(
    state : AddLeadState,
    focusManager: FocusManager,
    onAction: (AddLeadAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryViolet,
                1f to PrimaryGreen
            )
        ).padding(16.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End).padding(16.dp),
            onClick = {
                onAction(AddLeadAction.OnBackClick)
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Datos del cliente",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
        )
        KottieAnimationUtil(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(12.dp).aspectRatio(3f),
            fileRoute = "files/anim_customer.json"
        )
        ExpisedDropdownMenuTypeClient(
            title = "Tipo de cliente",
            listOptions = listOf(
                TypeOfClient.NUEVO,
                TypeOfClient.DESARROLLO,
                TypeOfClient.RECUPERACION
            ),
            optionSelectable = state.typeClient,
            optionSelectableClick = {
                onAction(AddLeadAction.OnTypeClientChange(it))
            }
        )
        ProSalesTextField(
            title = "Razon social",
            state = state.nameCompany,
            colors = Color.White,
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
                onAction(AddLeadAction.OnRazonSocialChange(it))
            },
            startIcon = Icons.Filled.Business
        )
        ProSalesTextField(
            title = "Nombre completo",
            state = state.contactName,
            colors = Color.White,
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
                onAction(AddLeadAction.OnFullNameChange(it))
            },
            startIcon = Icons.Filled.Person
        )
        ProSalesTextField(
            title = "Correo electronico",
            colors = Color.White,
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
                onAction(AddLeadAction.OnEmailChange(it))
            },
            startIcon = Icons.Filled.Email
        )
        ProSalesTextField(
            title = "Numero de telefono",
            colors = Color.White,
            state = state.phoneNumber,
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
                onAction(AddLeadAction.OnPhoneNumberChange(it))
            },
            startIcon = Icons.Filled.Phone
        )
        ProSalesTextField(
            title = "Direccion",
            colors = Color.White,
            state = state.fiscalAddress,
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
                onAction(AddLeadAction.OnAddressFiscalChange(it))
            },
            startIcon = Icons.Filled.Map
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        ProSalesActionButton(
            text = "Guardar",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.ADD_MORE_INFO_LEAD))
            }
        )
    }
}