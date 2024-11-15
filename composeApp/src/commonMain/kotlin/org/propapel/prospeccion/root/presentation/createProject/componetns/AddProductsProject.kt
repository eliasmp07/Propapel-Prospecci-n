package org.propapel.prospeccion.root.presentation.createProject.componetns

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.presentation.addlead.components.ProductsInterestedItem
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProSalesPriceTextField
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectAction
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectState

@Composable
fun AddProductsProject(
    state: CreateProjectState,
    onAction: (CreateProjectAction) -> Unit
) {
    var expandedProducts by remember {
        mutableStateOf(false)
    }
    Column (
        modifier = Modifier.fillMaxSize().padding(
            16.dp
        )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().weight(0.8f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Agregar productos al proyecto",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            item {
                ExposedDropdownMenuGereric(
                    title = "Productos",
                    state = expandedProducts,
                    colors = Color.Black,
                    onDimiss = {
                        expandedProducts = !expandedProducts
                    },
                    listOptions = state.purchases,
                    content = {
                        DropdownMenuItem(
                            text = { Text(text = it.productServiceName) },
                            onClick = {
                                expandedProducts = !expandedProducts
                                onAction(CreateProjectAction.OnChangeProduct(it))
                            }
                        )
                    }
                )
            }
            items(
                state.productsProject,
                key = {
                    it.purcheseId
                }) {
                ItemProduct(
                    purchase = it,
                    resultValue = {
                        onAction(CreateProjectAction.OnUpdateAmountProduct(it))
                    },
                    onRemove = {
                        onAction(CreateProjectAction.OnRemoveProduct(it))
                    }
                )
            }
        }
        ButtonsContinueCreateProjectScreen(
            onNext = {
                onAction(CreateProjectAction.OnNextScreen(CreateProjectScreenState.CONFIRM_PROJECT))
            },
            onPreview = {
                onAction(CreateProjectAction.OnNextScreen(CreateProjectScreenState.ADD_INFO_PROJECT))
            }
        )
    }
}

@Composable
fun ItemProduct(
    purchase: Purchase,
    resultValue: (Purchase) -> Unit,
    onRemove: (Purchase) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var showEditValue by remember { mutableStateOf(false) }
    var rememberValueAmount by remember { mutableStateOf(purchase.amount) }
    var valueUpdate by remember { mutableStateOf(purchase.amount) } // Nuevo estado para el valor actualizado

    ElevatedCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = purchase.productServiceName)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (showEditValue) SuccessGreen else IconButtonDefaults.iconButtonColors().containerColor
                    ),
                    onClick = {
                        showEditValue = !showEditValue
                        if (!showEditValue) {
                            focusManager.clearFocus()
                        } else {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                }
                IconButton(
                    onClick = {
                        onRemove(purchase)
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().animateContentSize()
            ) {
                ProSalesPriceTextField(
                    modifier = Modifier.weight(0.8f),
                    state = valueUpdate, // Usamos el valor actualizado en lugar de `purchase.amount`
                    roundedCornerShape = 8.dp,
                    enabled = showEditValue,
                    onTextChange = { newValue ->
                        valueUpdate = newValue // Actualizamos el valor temporal
                    }
                )
                AnimatedVisibility(
                    visible = valueUpdate != rememberValueAmount, // Mostrar solo si hay cambios
                ) {
                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = {
                          if (valueUpdate.isNotEmpty()){
                              rememberValueAmount = valueUpdate // Guardamos el nuevo valor
                              resultValue(
                                  Purchase(
                                      purcheseId = purchase.purcheseId,
                                      productServiceName = purchase.productServiceName,
                                      amount = valueUpdate,
                                      purchaseDate = purchase.purchaseDate,
                                      isIntoProduct = purchase.isIntoProduct
                                  )
                              ) // Enviamos el valor actualizado
                              showEditValue = false // Cerramos el modo edición
                          }
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Save, contentDescription = null)
                    }
                }
            }
        }
    }
}
