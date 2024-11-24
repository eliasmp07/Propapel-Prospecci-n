@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.root.presentation.addlead.components.utils

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient

@Composable
fun ExpisedDropdownMenuTypeClient(
    modifier: Modifier = Modifier,
    title: String  = "Tipo de cliente",
    colors: Color = Color.White,
    optionSelectable: TypeOfClient = TypeOfClient.NUEVO,
    optionSelectableClick: (TypeOfClient) -> Unit,
    listOptions: List<TypeOfClient> = listOf()
){
    var state by remember {
        mutableStateOf(
            false
        )
    }
    ExposedDropdownMenuBox(
        expanded = state,
        onExpandedChange = {
            //state = !state
        },
        modifier = modifier
    ) {

        ProSalesTextField(
            modifierTextField = Modifier.clickable {
                state = !state
            },
            colors = colors,
            readOnly = true,
            state = optionSelectable.name,
            startIcon = null,
            endIcon = if (state) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
            hint = "",
            title = title,
            onTextChange = {

            }
        )

        ExposedDropdownMenu(
            expanded = state,
            onDismissRequest = {
                state = !state
            }) {
            listOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.name) },
                    onClick = {
                        optionSelectableClick(option)
                        state = !state
                    }
                )
            }
        }
    }
}
