package org.propapel.prospeccion.root.domain.models

data class Project(
    val id: Int = 0,
    val nameProject: String = "",
    val progress: Int = 0,
    val status: String = "",
    val prioridad: String = "",
    val valorProject: String = "",
    val isCancel: Boolean = false,
    val customer: Customer = Customer(),
    val products: List<Purchase> = listOf()
)
