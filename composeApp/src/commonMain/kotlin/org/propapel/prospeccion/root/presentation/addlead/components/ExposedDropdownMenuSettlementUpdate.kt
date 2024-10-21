@file:OptIn(ExperimentalMaterialApi::class)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.clickable
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
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField

@Composable
fun ExposedDropdownMenuSettlementUpdate(
    modifier: Modifier = Modifier,
    title: String  = "",
    optionSelectable: String = "",
    optionSelectableClick: (String) -> Unit,
    listOptions: List<String> = listOf()
) {
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
                state = !state
            }) {
            listOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        optionSelectableClick(option)
                        state = !state
                    }
                )
            }
        }
    }
}

