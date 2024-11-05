package org.propapel.prospeccion.root.presentation.createInteraction

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProductsPropapel

class CreateInteractionViewModel(

) : ViewModel(){
    private val _state = MutableStateFlow(CreateInteractionLeadState())
    val state: StateFlow<CreateInteractionLeadState> get() = _state.asStateFlow()

    fun onAction(
        action: CreateInteractionAction
    ){
        when(action){
            is CreateInteractionAction.OnNextScreen -> {
                if (action.screen == CreateInteractionScreenState.FINISH){
                    createCustomer()
                }else{
                    _state.update {
                        it.copy(
                            screenState = action.screen
                        )
                    }
                }
            }
            is CreateInteractionAction.OnBackClick -> {
                _state.value = _state.value.copy(screenState = CreateInteractionScreenState.InfoInteractionScreen)
            }
            is CreateInteractionAction.OnTypeClientChange -> {
                _state.value = _state.value.copy(typeClient = action.typeClient)
            }
            is CreateInteractionAction.OnNoteAppointmentChange -> {
                _state.value = _state.value.copy(notesAppointment = action.notes)
            }
            is CreateInteractionAction.OnProductInterestedChange -> {
                _state.value = _state.value.copy(productInterested = action.product)
            }
            is CreateInteractionAction.OnPriceChange -> {
                _state.value = _state.value.copy(price = action.price)
            }
            is CreateInteractionAction.OnAddProductClick -> {
                _state.update {
                    val products = it.productsIntereses.toMutableList()
                    products.add(
                        PurchaseRequest(
                            productServiceName = it.productInterested.name,
                            purchaseDate = Clock.System.now().toEpochMilliseconds(),
                            amount = if (it.price.isEmpty()) 0.0 else it.price.replace("$", "").toDouble()
                        )
                    )
                    it.copy(
                        price = "",
                        productsIntereses = products,
                        productInterested = ProductsPropapel.PAPELERIA,
                    )
                }
            }
            is CreateInteractionAction.OnRemoveProductClick -> {
                _state.update {
                    val products = it.productsIntereses.toMutableList()
                    products.remove(action.product)
                    it.copy(
                        productsIntereses = products
                    )
                }
            }
        }
    }

    private fun createCustomer(){

    }
}