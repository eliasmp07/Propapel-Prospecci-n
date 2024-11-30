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
import kotlinx.datetime.Clock
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ProductRepository
import org.propapel.prospeccion.root.domain.repository.ProjectRepository

class CreateProjectViewModel(
    private val projectRepository: ProjectRepository,
    private val productRepository: ProductRepository,
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
                // Buscar el índice del producto en la lista actual
                val products = _state.value.productsProject.toMutableList()
                val index = products.indexOfFirst { it.purcheseId == action.product.purcheseId }

                if (index != -1) {
                    // Crear una copia del producto con el nuevo valor de `amount`
                    val updatedProduct = products[index].copy(
                        amount = action.product.amount// Aquí asegúrate de pasar el valor actualizado
                    )

                    // Reemplazar el producto en la lista
                    products[index] = updatedProduct

                    // Actualizar el estado con la lista modificada
                    _state.update {
                        it.copy(
                            productsProject = products
                        )
                    }
                }
            }
            is CreateProjectAction.OnPriceProductChange ->{
                _state.update {
                    it.copy(
                        amoutProduct = action.price
                    )
                }
            }
            is CreateProjectAction.OnProductNameChange -> {
                _state.update {
                    it.copy(
                        productServiceName = action.product
                    )
                }
            }
            CreateProjectAction.OnCreateProductClick -> {
                createProduct()
            }
            is CreateProjectAction.OnCreateProject -> {
                createProject(action.totalProyect)
            }
            else -> Unit
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
                        val purchasesFilter = result.data.purchase.filter {purchase ->
                            !purchase.isIntoProduct
                        }
                        it.copy(
                            purchases = purchasesFilter,
                            customer = result.data
                        )
                    }
                }
            }
        }

    }

    private fun createProduct(){
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            _state.update {
                it.copy(
                    isCreatingProduct = true
                )
            }
            val result = productRepository.create(
                customerId = _state.value.customer.idCustomer,
                amount = _state.value.amoutProduct.toDouble(),
                productServiceName = _state.value.productServiceName,
                purchaseDate = Clock.System.now().toEpochMilliseconds()
            )

            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            isCreatingProduct = false
                        )
                    }
                }
                is ResultExt.Success -> {
                    val products = _state.value.productsProject.toMutableList()
                    products.add(
                        Purchase(
                            purcheseId = result.data.purcheseId,
                            productServiceName = result.data.productServiceName,
                            isIntoProduct = true,
                            amount = result.data.amount,
                            purchaseDate = result.data.purchaseDate
                        )
                    )
                    _state.update {
                        it.copy(
                            successCreateProduct = !it.successCreateProduct,
                            isCreatingProduct = false,
                            productServiceName = "Selecciona una opción",
                            amoutProduct = "",
                            productsProject = products
                        )
                    }
                }
            }
        }
    }

    private fun createProject(
        totalProject: Double
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isCreatingProduct = true
                )
            }
            val result = projectRepository.createProject(
                customerId = _state.value.customer.idCustomer.toString(),
                nameProject = _state.value.nameProject,
                totalProject = totalProject,
                stateProeject = _state.value.stateProject,
                priority = _state.value.priorityProject,
                progressProject = 90,
                products = _state.value.productsProject
            )

            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            isCreatingProduct = false,
                            stateScreen = CreateProjectScreenState.ERROR_CREATE
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            isCreatingProduct = false,
                            stateScreen = CreateProjectScreenState.SUCCESS_CREATE
                        )
                    }
                }
            }
        }
    }
}

expect fun formatString(value:Double): String