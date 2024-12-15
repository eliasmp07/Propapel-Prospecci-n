package org.propapel.prospeccion.root.presentation.searchLead.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

/**
 * Clase Point personalizada para manejar las coordenadas.
 */
data class Point(val x: Int, val y: Int)

/**
 * Staggered grid layout for displaying items as GridLayout in classic View
 */
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        content = content,
        modifier = modifier
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val constraintMaxWidth = constraints.maxWidth
        val constraintMaxHeight = constraints.maxHeight

        var maxRowWidth = 0

        var currentWidthOfRow = 0
        var totalHeightOfRows = 0

        var xPos: Int
        var yPos: Int

        // Usamos la clase Point personalizada en lugar de android.graphics.Point
        val placeableMap = linkedMapOf<Int, Point>()
        val rowHeights = mutableListOf<Int>()

        var maxPlaceableHeight = 0
        var lastRowHeight = 0

        val placeables: List<Placeable> = measurables.mapIndexed { index, measurable ->
            // Medir cada hijo
            val placeable = measurable.measure(constraints)
            val placeableWidth = placeable.width
            val placeableHeight = placeable.height

            val isSameRow = (currentWidthOfRow + placeableWidth <= constraintMaxWidth)

            if (isSameRow) {

                xPos = currentWidthOfRow
                yPos = totalHeightOfRows

                // El ancho de la fila es la longitud existente más la longitud del nuevo ítem
                currentWidthOfRow += placeableWidth

                // Obtener la altura máxima de los ítems en cada fila
                maxPlaceableHeight = maxPlaceableHeight.coerceAtLeast(placeableHeight)

                // Después de agregar cada ítem, verificar si es la fila más larga
                maxRowWidth = maxRowWidth.coerceAtLeast(currentWidthOfRow)

                lastRowHeight = maxPlaceableHeight

            } else {

                currentWidthOfRow = placeableWidth
                maxPlaceableHeight = maxPlaceableHeight.coerceAtLeast(placeableHeight)

                totalHeightOfRows += maxPlaceableHeight

                xPos = 0
                yPos = totalHeightOfRows

                rowHeights.add(maxPlaceableHeight)

                lastRowHeight = maxPlaceableHeight
                maxPlaceableHeight = placeableHeight
            }

            // Usamos la clase Point personalizada
            placeableMap[index] = Point(xPos, yPos)
            placeable
        }

        val finalHeight = (rowHeights.sumOf { it } + lastRowHeight)
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Establecer el tamaño del layout lo más grande posible
        layout(maxRowWidth, finalHeight) {
            // Colocar los hijos en el layout padre
            placeables.forEachIndexed { index, placeable ->
                // Posicionar el ítem en la pantalla

                val point = placeableMap[index]
                point?.let {
                    placeable.placeRelative(x = it.x, y = it.y)
                }
            }
        }
    }
}
