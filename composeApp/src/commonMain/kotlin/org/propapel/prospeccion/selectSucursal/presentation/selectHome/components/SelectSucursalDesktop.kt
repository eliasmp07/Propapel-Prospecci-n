package org.propapel.prospeccion.selectSucursal.presentation.selectHome.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.ItemSucusal
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalAction
import org.propapel.prospeccion.selectSucursal.presentation.selectHome.SelectSucursalState

@Composable
fun SelectSucursalDesktop(
    state: SelectSucursalState,
    listState: LazyListState,
    onAction: (SelectSucursalAction) -> Unit
){
    ElevatedCard {
        Column(
            modifier = Modifier.fillMaxSize().animateContentSize()
        ) {
            Spacer(
                modifier = Modifier.height(32.dp)
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Bienvenido ${state.authInfo.name}, selecione una sucursal, para comenzar",
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón izquierda
                IconButton(
                    modifier = Modifier.weight(0.1f),
                    onClick = {
                        onAction(SelectSucursalAction.OnLeftSelectedClick)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                // Lista de sucursales
                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.8f),
                    state = listState,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    items(state.sucusales) { sucursal ->
                        ItemSucusal(
                            sucusales = sucursal,
                            selected = state.sucusalId == sucursal.id,
                            onSelect = {
                                onAction(SelectSucursalAction.OnSucusalChange(it))
                            }
                        )
                    }
                }

                // Botón derecha
                IconButton(
                    modifier = Modifier.weight(0.1f),
                    onClick = {
                        onAction(SelectSucursalAction.OnRightSelectedClick)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            // Botón continuar
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally).padding(16.dp),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut(),
                visible = state.sucusalId != 0
            ){
                Button(
                    onClick = {
                        onAction(SelectSucursalAction.OnSucursalSelectedClick(state.sucusalId))
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFF6363),
                                    Color(0xFFAB47BC)
                                )
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = "Continuar", color = Color.White)
                }
            }
        }
    }
}