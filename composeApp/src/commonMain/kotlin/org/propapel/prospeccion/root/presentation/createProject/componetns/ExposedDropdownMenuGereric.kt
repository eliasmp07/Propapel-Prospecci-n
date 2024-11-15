@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.createProject.componetns

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectAction

@Composable
fun <T>ExposedDropdownMenuGereric(
    modifier: Modifier = Modifier,
    state: Boolean = false,
    title: String,
    colors: Color = Color.White,
    onDimiss: () -> Unit,
    optionSelectable: String = "Selecione una opcion",
    content: @Composable() (ColumnScope.(T) -> Unit),
    listOptions: List<T> = listOf()
) {
    ExposedDropdownMenuBox(
        expanded = state,
        onExpandedChange = {
            //state = !state
        },
        modifier = modifier
    ) {

        ProSalesTextField(
            modifierTextField = Modifier.clickable {
                onDimiss()
            },
            colors = colors,
            readOnly = true,
            state = optionSelectable,
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
                onDimiss()
            }) {
            DropdownMenuItem(
                text = { Text(text = "Seleccione una opcion") },
                onClick = {
                }
            )
            listOptions.forEach { option ->
                content(option)
            }
        }
    }
}
