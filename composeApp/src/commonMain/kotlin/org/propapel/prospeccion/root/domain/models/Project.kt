package org.propapel.prospeccion.root.domain.models

import kotlinx.datetime.LocalDate
import org.propapel.prospeccion.core.presentation.ui.TimeUtils

data class Project(
    val id: Int = 0,
    val nameProject: String = "",
    val progress: Int = 0,
    val status: String = "",
    val prioridad: String = "",
    val created: LocalDate= TimeUtils.DateNow,
    val valorProject: String = "",
    val isCancel: Boolean = false,
    val customer: Customer = Customer(),
    val products: List<Purchase> = listOf()
){

}
