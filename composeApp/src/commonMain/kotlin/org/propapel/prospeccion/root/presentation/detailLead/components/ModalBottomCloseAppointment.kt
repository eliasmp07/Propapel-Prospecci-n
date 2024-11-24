package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.addlead.components.ProductsInterestedItem
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProSalesPriceTextField
import org.propapel.prospeccion.root.presentation.createProject.componetns.ExposedDropdownMenuGereric
import org.propapel.prospeccion.root.presentation.createProject.componetns.provideProductsPropapel
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadSMState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomCloseAppointment(
    state: DetailLeadSMState,
    sheetState: SheetState,
    onAction: (DetailLeadAction) -> Unit,
    scope: CoroutineScope = rememberCoroutineScope(),
    onDismissRequest: () -> Unit
) {
    var stateBottomSheet by remember {
        mutableStateOf(StateBottomSheetCloseAppointment.CONFIRM_CLOSE)
    }
    ModalBottomSheet(
        modifier = Modifier.systemBarsPadding(),
        onDismissRequest = {
            onDismissRequest()
        },
        dragHandle = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissRequest()
                            }
                        }
                    },
                    content = {
                        Text(
                            text = "Cancelar",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                )
            }
        },
        sheetState = sheetState
    ) {
        HorizontalDivider()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Cerrando cita",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            AnimatedContent(
                targetState = stateBottomSheet
            ) { screenState ->
                when (screenState) {
                    StateBottomSheetCloseAppointment.CONFIRM_CLOSE -> {
                        Column {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "En tu cita el cliente se intereso en algun producto o servicio de Propapel",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                            ProSalesActionButtonOutline(
                                text = "Si",
                                onClick = {
                                    stateBottomSheet = StateBottomSheetCloseAppointment.ADD_PRODUCT
                                }
                            )
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                            ProSalesActionButtonOutline(
                                text = "No",
                                onClick = {

                                }
                            )
                        }
                    }
                    StateBottomSheetCloseAppointment.ADD_PRODUCT -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    val focusManager = LocalFocusManager.current

                                    Text(
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        text = "Productos de interes",
                                        fontWeight = FontWeight.ExtraBold,
                                        style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                                    )
                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )
                                    var expandedProducts by remember {
                                        mutableStateOf(false)
                                    }
                                    ExposedDropdownMenuGereric(
                                        title = "Productos Propapel",
                                        state = expandedProducts,
                                        listOptions = provideProductsPropapel(),
                                        onDimiss = {
                                            expandedProducts = !expandedProducts
                                        },
                                        colors = Color.Black,
                                        optionSelectable = state.productInterest,
                                        content = {
                                            DropdownMenuItem(
                                                text = { androidx.compose.material.Text(text = it.name) },
                                                onClick = {
                                                    expandedProducts = !expandedProducts
                                                    onAction(DetailLeadAction.OnProductInterestChange(it.name))
                                                }
                                            )
                                        }
                                    )
                                    Text(
                                        "*Puede agregar mas de un producto",
                                        color = Color.Gray
                                    )
                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )
                                    ProSalesPriceTextField(
                                        state = state.price,
                                        startIcon = Icons.Default.Sell,
                                        title = "Potencial de venta",
                                        onTextChange = {
                                            onAction(DetailLeadAction.OnPriceChange(it))
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onDone = {
                                                focusManager.clearFocus()
                                                onAction(DetailLeadAction.OnAddProductClick)
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
                                            onAction(DetailLeadAction.OnAddProductClick)
                                        }
                                    )
                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )
                                }
                            }
                            items(state.productsInterest) {
                                ProductsInterestedItem(
                                    purchaseRequest = it,
                                    onRemove = {
                                        onAction(DetailLeadAction.OnRemoveProductInterestClick(it))
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
                                        text = "Cerrar cita",
                                        isLoading = false,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.White
                                        ),
                                        onClick = {
                                            onAction(DetailLeadAction.OnCloseAppointment)
                                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                                if (!sheetState.isVisible) {
                                                    onDismissRequest()
                                                }
                                            }
                                        }
                                    )
                                    Spacer(
                                        modifier = Modifier.height(32.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class StateBottomSheetCloseAppointment {
    CONFIRM_CLOSE, ADD_PRODUCT
}
