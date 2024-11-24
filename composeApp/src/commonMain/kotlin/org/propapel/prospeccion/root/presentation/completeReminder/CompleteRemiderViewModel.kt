package org.propapel.prospeccion.root.presentation.completeReminder

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import org.propapel.prospeccion.root.domain.models.PurchaseRequest

class CompleteReminderViewModel: ViewModel() {
    private var _state = MutableStateFlow(CompleteReminderState())
    val state: StateFlow<CompleteReminderState> get() = _state.asStateFlow()

    fun onAction(
        action: CompleteReminderAction
    ){
        when(action){
            CompleteReminderAction.OnAddProductClick -> {
                _state.update {
                    val products = it.productsInterest.toMutableList()
                    products.add(
                        PurchaseRequest(
                            productServiceName = it.productInterest,
                            purchaseDate = Clock.System.now().toEpochMilliseconds(),
                            amount = if (it.price.isEmpty()) 0.0 else it.price.replace("$", "").toDouble()
                        )
                    )
                    it.copy(
                        price = "",
                        productsInterest = products,
                        productInterest = "Selecciona una opción",
                    )
                }
            }
            is CompleteReminderAction.OnNextScreenClick -> {
                _state.update {
                    it.copy(
                        screenState = action.screen
                    )
                }
            }
            is CompleteReminderAction.OnPriceChange -> {
                _state.update {
                    it.copy(
                        price = action.price
                    )
                }
            }
            is CompleteReminderAction.OnProductInterestChange -> {
                _state.update {
                    it.copy(productInterest = action.product)
                }
            }
            is CompleteReminderAction.OnTypeDateChange -> {
                _state.update {
                    it.copy(
                        typeDate = action.typeInteraction
                    )
                }
            }
            is CompleteReminderAction.OnNoteChange -> {
                _state.update {
                    it.copy(
                        notes = action.note
                    )
                }
            }
            is CompleteReminderAction.OnRemoveProductInterestClick ->{
                _state.update {
                    val products = it.productsInterest.toMutableList()
                    products.remove(action.product)
                    it.copy(
                        productsInterest = products
                    )
                }
            }
            else -> Unit
        }
    }
}