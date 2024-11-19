package org.propapel.prospeccion.root.data.mappers

import org.propapel.prospeccion.root.data.dto.project.ProjectResponse
import org.propapel.prospeccion.root.domain.models.Project

fun ProjectResponse.toProject(): Project{
    return Project(
        id = id,
        customer = customer.toCustomer(),
        nameProject = nameProject,
        products = products?.map {
            it.toPurchase()
        }?: listOf(),
        prioridad = prioridad?:"",
        valorProject = valorProject,
        status = status,
        progress = progress,
        isCancel = isCancel
    )
}