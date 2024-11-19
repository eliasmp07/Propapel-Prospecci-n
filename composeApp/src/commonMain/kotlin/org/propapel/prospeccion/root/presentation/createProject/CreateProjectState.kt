package org.propapel.prospeccion.root.presentation.createProject

import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.domain.models.PurchaseRequest

data class CreateProjectState(
    val stateScreen: CreateProjectScreenState = CreateProjectScreenState.WELCOME,
    val customer: Customer = Customer(),
    val purchases: List<Purchase> = listOf(),
    val stateProject: String = "Seleccione una opción",
    val priorityProject: String = "Seleccione una opcion",
    val nameProject: String ="",
    val progressProyect: String = "",
    val productsProject: List<Purchase> = listOf(),
    val productServiceName: String = "Seleciona una opcion",
    val successCreateProduct: Boolean = false,
    val amoutProduct: String = "",
    val isCreatingProduct: Boolean = false
)
