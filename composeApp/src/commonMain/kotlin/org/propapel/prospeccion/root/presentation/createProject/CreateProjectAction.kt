package org.propapel.prospeccion.root.presentation.createProject

import org.propapel.prospeccion.root.domain.models.Purchase

sealed interface CreateProjectAction {
    data object OnBackClick: CreateProjectAction
    data class OnNextScreen(val screen: CreateProjectScreenState): CreateProjectAction
    data class OnChangeProduct(val product: Purchase): CreateProjectAction
    data class OnRemoveProduct(val product: Purchase): CreateProjectAction
    data class OnChangeNameProject(val nameProject: String): CreateProjectAction
    data class OnStateProject(val stateProject: String): CreateProjectAction
    data class OnChangePriotityProject(val priority: String) : CreateProjectAction
    data class OnUpdateAmountProduct(val product: Purchase): CreateProjectAction
    data class OnPriceProductChange(val price: String): CreateProjectAction
    data class OnProductNameChange(val product: String): CreateProjectAction
    data object OnCreateProductClick: CreateProjectAction
    data class OnCreateProject(val totalProyect: Double): CreateProjectAction
}