package org.propapel.prospeccion.root.presentation.homeRoot.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField


@Composable
fun <T> DropdownListObjects(
    modifier: Modifier = Modifier,
    title: String = "Opción",
    text: String = "",
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    hint: String = "Buscar",
    expanded: Boolean,
    content: LazyListScope.(List<T>) -> Unit,
    listOptions: List<T> = listOf()
) {

    Column(modifier = modifier.heightIn(max = 200.dp)) {
        ProSalesTextField(
            state = text,
            title = title,
            onTextChange = onValueChange,
            hint = hint,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    // Puedes usar esto si necesitas obtener la posición del TextField
                }.onPreviewKeyEvent {
                    if (it.key == Key.Enter){
                        onSearch()
                        true
                    }else{
                        false
                    }
                },
        )
        // Mostrar la lista de opciones si el menú está expandido
        if (expanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp) // Limita la altura de la lista
            ) {
                content(listOptions)
            }
        }
    }
}
