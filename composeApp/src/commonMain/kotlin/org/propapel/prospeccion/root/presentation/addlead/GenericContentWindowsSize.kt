package org.propapel.prospeccion.root.presentation.addlead

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp

/**
 * Contenido para manejar el tamaño para escritorio
 *
 * @param modifier Modificador para el composable
 * @param brush Brush para aplicar color al composable
 * @param onCloseScreen Lambda que sale de la pantalla
 * @param content1 Contenido 1 para imagenes o alguna grafica
 * @param content2 Contenido 2 para campos de texto o informacion
 */
@Composable
fun GenericContentWindowsSize(
    modifier: Modifier = Modifier,
    brush: Brush,
    onCloseScreen :() -> Unit,
    content1: @Composable RowScope.() -> Unit,
    content2: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.background(
            brush = brush
        ).fillMaxSize().padding(16.dp)
    ){
        IconButton(
            modifier = Modifier.align(Alignment.End).padding(16.dp).pointerHoverIcon(PointerIcon.Hand),
            onClick = {
                onCloseScreen()
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content1()
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                content2()
            }
        }
    }
}