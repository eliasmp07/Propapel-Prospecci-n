package org.propapel.prospeccion.root.presentation.createInteraction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.root.domain.models.Interaction
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.repository.InteractionRepository

class CreateInteractionViewModel(
    private val interactionRepository: InteractionRepository
) : ViewModel(){

    private val _state = MutableStateFlow(CreateInteractionLeadState())
    val state: StateFlow<CreateInteractionLeadState> get() = _state.asStateFlow()


    fun onChangeIdCustomer(idCustomer: String){
        _state.update {
            it.copy(
                idCustomer = idCustomer,
            )
        }
    }

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
                            productServiceName = it.productInterested,
                            purchaseDate = Clock.System.now().toEpochMilliseconds(),
                            amount = if (it.price.isEmpty()) 0.0 else it.price.replace("$", "").toDouble()
                        )
                    )
                    it.copy(
                        price = "",
                        productsIntereses = products,
                        productInterested = "Selecione una opción",
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
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isCreatingInteraction = true,
                    isSuccessCreate = false
                )
            }
            val result = interactionRepository.create(
                idCustomer = _state.value.idCustomer,
                interaction = Interaction(
                    interactionType = _state.value.typeClient.name,
                    interactionDate = Clock.System.now().toEpochMilliseconds(),
                    notes = _state.value.notesAppointment,
                ),
                purchese = _state.value.productsIntereses,
            )

            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            isCreatingInteraction = false,
                            isSuccessCreate = false
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            isSuccessCreate = true,
                            isCreatingInteraction = false
                        )
                    }
                }
            }
        }
    }
}