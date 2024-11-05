package org.propapel.prospeccion.root.presentation.createInteraction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPink
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPinkBlended
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.components.ProductsInterestedItem
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ExposedDropdownMenuProductsInterested
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProSalesPriceTextField
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProductsPropapel
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionAction
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionLeadState
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionScreenState

@Composable
fun AddInfoProductsInteresedClient(
    state: CreateInteractionLeadState,
    onAction: (CreateInteractionAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryPinkBlended,
                1f to PrimaryPink
            )
        ).padding(16.dp)
    ) {
        item {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                IconButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        onAction(CreateInteractionAction.OnBackClick)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
            }

        }
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Productos de interes",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ExposedDropdownMenuProductsInterested(
                    optionSelectable = state.productInterested,
                    listOptions = listOf(
                        ProductsPropapel.ROLLITOS,
                        ProductsPropapel.INSTALACION_RACKS,
                        ProductsPropapel.INSTALACION_CAMARA,
                        ProductsPropapel.RENTA_IMPRESORA,
                        ProductsPropapel.RENTA_EQUIPO_DE_COMPUTO,
                        ProductsPropapel.PAPELERIA,
                        ProductsPropapel.TOTAL_OFFICE
                    ),
                    optionSelectableClick = {
                        onAction(CreateInteractionAction.OnProductInterestedChange(it))
                    }
                )
                Text("*Puede agregar mas de un producto", color = Color.White)
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ProSalesPriceTextField(
                    state = state.price,
                    startIcon = Icons.Default.Sell,
                    colors = Color.White,
                    title = "Potencial de venta",
                    onTextChange = {
                        onAction(CreateInteractionAction.OnPriceChange(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onAction(CreateInteractionAction.OnAddProductClick)
                        }
                    )
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ProSalesActionButton(
                    text = "Agregar producto",
                    isLoading = false,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    onClick = {
                        focusManager.clearFocus()
                        onAction(CreateInteractionAction.OnAddProductClick)
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        }
        items(state.productsIntereses){
            ProductsInterestedItem(
                purchaseRequest = it,
                onRemove = {
                    onAction(CreateInteractionAction.OnRemoveProductClick(it))
                }
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                ProSalesActionButton(
                    text = "Finalizar",
                    isLoading = false,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    onClick = {
                        onAction(CreateInteractionAction.OnNextScreen(CreateInteractionScreenState.FINISH))
                    }
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
            }
        }
    }
}