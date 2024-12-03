package org.propapel.prospeccion.root.presentation.createProject

import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Purchase

data class CreateProjectState(
    val nameProjectError: String? = null,
    val priceError: String? = null,
    val nameProductError: String? = null,
    val stateScreen: CreateProjectScreenState = CreateProjectScreenState.WELCOME,
    val customer: Customer = Customer(),
    val purchases: List<Purchase> = listOf(),
    val stateProject: String = "En negociacion",
    val priorityProject: String = "Baja",
    val nameProject: String ="",
    val progressProyect: String = "",
    val productsProject: List<Purchase> = listOf(),
    val productServiceName: String = "",
    val successCreateProduct: Boolean = false,
    val amoutProduct: String = "",
    val isCreatingProduct: Boolean = false
)
