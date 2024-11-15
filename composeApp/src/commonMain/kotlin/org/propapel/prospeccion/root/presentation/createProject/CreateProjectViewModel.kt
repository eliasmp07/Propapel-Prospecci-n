package org.propapel.prospeccion.root.presentation.createProject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ProjectRepository

class CreateProjectViewModel(
    private val projectRepository: ProjectRepository,
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateProjectState())
    val state: StateFlow<CreateProjectState> get() = _state.asStateFlow()

    fun onAction(
        action: CreateProjectAction
    ) {
        when (action) {
            is CreateProjectAction.OnNextScreen -> {
                _state.update {
                    it.copy(
                        stateScreen = action.screen
                    )
                }
            }
            is CreateProjectAction.OnChangeProduct -> {
                val purchases = _state.value.purchases.toMutableList()
                val index = purchases.indexOfFirst {
                    it.purcheseId == action.product.purcheseId
                }
                purchases.removeAt(index)
                val products = _state.value.productsProject.toMutableList()
                if (!products.contains(action.product)) {
                    products.add(
                        Purchase(
                            purcheseId = action.product.purcheseId,
                            productServiceName = action.product.productServiceName,
                            isIntoProduct = true,
                            amount = action.product.amount,
                            purchaseDate = action.product.purchaseDate
                        )
                    )
                    _state.update {
                        it.copy(
                            purchases = purchases,
                            productsProject = products
                        )
                    }
                }
            }
            is CreateProjectAction.OnRemoveProduct -> {
                val products = _state.value.productsProject.toMutableList()
                val index = products.indexOfFirst { it.purcheseId == action.product.purcheseId }
                val purchases = _state.value.purchases.toMutableList()
                purchases.add(action.product)
                if (index != -1) {
                    products.removeAt(index)
                    _state.update {
                        it.copy(
                            purchases = purchases,
                            productsProject = products
                        )
                    }
                }
            }
            is CreateProjectAction.OnChangeNameProject -> {
                _state.update {
                    it.copy(
                        nameProject = action.nameProject
                    )
                }
            }
            is CreateProjectAction.OnChangePriotityProject -> {
                _state.update {
                    it.copy(
                        priorityProject = action.priority
                    )
                }
            }
            is CreateProjectAction.OnStateProject -> {
                _state.update {
                    it.copy(
                        stateProject = action.stateProject
                    )
                }
            }
            is CreateProjectAction.OnUpdateAmountProduct -> {
                val products = _state.value.productsProject
                val index = products.indexOf(action.product)

                if (index != -1) {
                    // Crear una copia del producto con el valor actualizado
                    val updatedProduct = products[index].copy(
                        amount = action.product.amount // Supongamos que `newAmount` es el valor actualizado
                    )

                    // Crear una nueva lista con el producto actualizado
                    val updatedProducts = products.toMutableList().apply {
                        this[index] = updatedProduct
                    }

                    // Actualizar el estado con la nueva lista
                    _state.update {
                        it.copy(
                            productsProject = updatedProducts
                        )
                    }

                }
            }
        }
    }

    fun getCustomerById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = customerRepository.getCustomerById(id)

            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            customer = Customer()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            purchases = result.data.purchase,
                            customer = result.data
                        )
                    }
                }
            }
        }

    }
}
