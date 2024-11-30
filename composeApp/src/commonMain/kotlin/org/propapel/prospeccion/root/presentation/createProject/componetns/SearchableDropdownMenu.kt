@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.root.presentation.createProject.componetns

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun <T> SearchableDropdownMenu(
    items: List<T>,
    onItemSelected: (T) -> Unit,
    itemLabelMapper: (T) -> String,
    openedIcon: ImageVector = Icons.Outlined.KeyboardArrowUp,
    closedIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    // Filtrar elementos según el texto ingresado
    val filteredItems = items.filter {
        itemLabelMapper(it).contains(searchQuery, ignoreCase = true)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    Column {
        TextField(
            value = selectedText,
            onValueChange = {  },
            label = { Text("Seleccionar") },
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconToggleButton(
                    checked = expanded,
                    onCheckedChange = {
                        expanded = it
                    },
                ) {
                    if (expanded) {
                        Icon(
                            imageVector = openedIcon,
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            imageVector = closedIcon,
                            contentDescription = null,
                        )
                    }
                }
            },interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        keyboardController?.show()
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                expanded = !expanded
                            }
                        }
                    }
                },
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Campo de búsqueda
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar") },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth().focusRequester(focusRequester),interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                focusRequester.requestFocus()
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {

                                    }
                                }
                            }
                        },
                )
                // Mostrar los elementos filtrados
                filteredItems.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = itemLabelMapper(item)
                            onItemSelected(item)
                            expanded = false
                            searchQuery = ""
                        },
                        text = { Text(itemLabelMapper(item)) }
                    )
                }
            }
        }
    }
}

