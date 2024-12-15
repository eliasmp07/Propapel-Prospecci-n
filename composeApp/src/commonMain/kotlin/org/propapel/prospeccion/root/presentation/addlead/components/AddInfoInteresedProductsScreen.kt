@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPink
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPinkBlended
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.GenericContentWindowsSize
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProSalesPriceTextField
import org.propapel.prospeccion.root.presentation.createProject.componetns.ExposedDropdownMenuGereric
import org.propapel.prospeccion.root.presentation.createProject.componetns.provideProductsPropapel
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.add_products_interested_img
import prospeccion.composeapp.generated.resources.ic_product_otline
import prospeccion.composeapp.generated.resources.interesting_product_img

@Composable
fun AddInfoInteresedProductsScreen(
    state: AddLeadState,
    focusManager: FocusManager,
    onAction: (AddLeadAction) -> Unit
) {

    val windowSizeClass = calculateWindowSizeClass()
    if (windowSizeClass.isMobile){
        AddInfoInterestedProductMobileScreen(
            state = state,
            onAction = onAction,
            focusManager = focusManager
        )
    }else{
        GenericContentWindowsSize(
            onCloseScreen = {
                onAction(AddLeadAction.OnBackClick)
            },
            brush =  Brush.verticalGradient(
                0f to PrimaryPinkBlended,
                1f to PrimaryPink
            ),
            content1 = {
                Box(
                    modifier = Modifier.weight(0.5f).padding(30.dp).height(400.dp).clip(CircleShape)
                ){
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(Res.drawable.add_products_interested_img),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                }
            },
            content2 = {
                LazyColumn(
                    modifier = Modifier.weight(0.5f).fillMaxSize().
                    padding(16.dp)
                ) {
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
                            var expandedProducts by remember {
                                mutableStateOf(false)
                            }
                            ExposedDropdownMenuGereric(
                                title = "Productos Propapel",
                                state = expandedProducts,
                                colors = Color.White,
                                listOptions = provideProductsPropapel(),
                                onDimiss = {
                                    expandedProducts = !expandedProducts
                                },
                                optionSelectable = state.productInterest,
                                content = {
                                    DropdownMenuItem(
                                        text = { androidx.compose.material.Text(text = it.toString()) },
                                        onClick = {
                                            expandedProducts = !expandedProducts
                                            onAction(AddLeadAction.OnProductInterestChange(it.name))
                                        }
                                    )
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
                                    onAction(AddLeadAction.OnPriceChange(it))
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                        onAction(AddLeadAction.OnAddProductClick)
                                    }
                                )
                            )
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                ProSalesActionButton(
                                    modifier = Modifier.weight(0.5f),
                                    text = "Agregar producto",
                                    isLoading = false,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ),
                                    onClick = {
                                        focusManager.clearFocus()
                                        onAction(AddLeadAction.OnAddProductClick)
                                    }
                                )
                                Spacer(
                                    modifier = Modifier.width(32.dp)
                                )
                                ProSalesActionButton(
                                    modifier = Modifier.weight(0.5f),
                                    text = "Continuar",
                                    isLoading = false,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ),
                                    onClick = {
                                        onAction(AddLeadAction.OnNextScreenClick(ContainerState.WANT_BOOK_AN_APPOINTMENT))
                                    }
                                )
                            }
                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                        }
                    }
                    items(state.productsInterest){
                        ProductsInterestedItem(
                            purchaseRequest = it,
                            onRemove = {
                                onAction(AddLeadAction.OnRemoveProductInterestClick(it))
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun AddInfoInterestedProductMobileScreen(
    state: AddLeadState,
    onAction: (AddLeadAction) -> Unit,
    focusManager: FocusManager,
){
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
                        onAction(AddLeadAction.OnBackClick)
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
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Productos de interes",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
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
                    colors = Color.White,
                    listOptions = provideProductsPropapel(),
                    onDimiss = {
                        expandedProducts = !expandedProducts
                    },
                    optionSelectable = state.productInterest,
                    content = {
                        DropdownMenuItem(
                            text = { androidx.compose.material.Text(text = it.toString()) },
                            onClick = {
                                expandedProducts = !expandedProducts
                                onAction(AddLeadAction.OnProductInterestChange(it.name))
                            }
                        )
                    }
                )
                Text("*Puede agregar mas de un producto", color = Color.White,overflow = TextOverflow.Ellipsis,)
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ProSalesPriceTextField(
                    state = state.price,
                    startIcon = Icons.Default.Sell,
                    colors = Color.White,
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
                            focusManager.clearFocus()
                            onAction(AddLeadAction.OnAddProductClick)
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
                        onAction(AddLeadAction.OnAddProductClick)
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        }
        items(state.productsInterest){
            ProductsInterestedItem(
                purchaseRequest = it,
                onRemove = {
                    onAction(AddLeadAction.OnRemoveProductInterestClick(it))
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
                    text = "Continuar",
                    isLoading = false,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    onClick = {
                        onAction(AddLeadAction.OnNextScreenClick(ContainerState.WANT_BOOK_AN_APPOINTMENT))
                    }
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
            }
        }
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
                    imageVector = vectorResource(Res.drawable.ic_product_otline),
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
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
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