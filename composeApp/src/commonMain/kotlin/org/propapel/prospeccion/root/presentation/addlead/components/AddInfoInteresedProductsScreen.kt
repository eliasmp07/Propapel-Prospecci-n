package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ExposedDropdownMenuProductsInterested
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProductsPropapel

@Composable
fun AddInfoInteresedProductsScreen(
    state: AddLeadState,
    onAction: (AddLeadAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Productos de interes",
            fontWeight = FontWeight.ExtraBold,
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ExposedDropdownMenuProductsInterested(
            optionSelectable = state.productInterest,
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
                onAction(AddLeadAction.OnProductInterestChange(it))
            }
        )
        Text("*Puede agregar mas de un producto")
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ProSalesTextField(
            state = state.price,
            startIcon = Icons.Default.Sell,
            hint = "$1000",
            title = "Potencial de venta",
            onTextChange = {
                onAction(AddLeadAction.OnPriceChange(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onAction(AddLeadAction.OnAddProductClick)
                }
            )
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ProSalesActionButton(
            text = "Guardar producto",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnAddProductClick)
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        state.productsInterest.forEach {
            ProductsInterestedItem(
                purchaseRequest = it,
                onRemove = {
                    onAction(AddLeadAction.OnRemoveProductInterestClick(it))
                }
            )
        }
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        ProSalesActionButton(
            text = "Continuar",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.WANT_BOOK_AN_APPOINTMENT))
            }
        )
    }
}

@Composable
fun ProductsInterestedItem(
    purchaseRequest: PurchaseRequest,
    onRemove :(PurchaseRequest) -> Unit
){
    ElevatedCard(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Rounded.TaskAlt,
                    contentDescription = "Tarea o pendiente"
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                Column {
                    Text(text = purchaseRequest.productServiceName , style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                   Row {
                       Text("Potencial de venta: ")
                       Text(text = "$${purchaseRequest.amount}" , style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                   }
                }
               Spacer(
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        onRemove(purchaseRequest)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null
                        )
                    }
                )
            }
            HorizontalDivider()
        }
    }

}