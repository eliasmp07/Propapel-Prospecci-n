package org.propapel.prospeccion.root.domain.repository

import org.propapel.prospeccion.core.domain.EmptyResult
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.Purchase

interface ProjectRepository {
    suspend fun createProject(
        nameProject: String,
        customerId: String,
        totalProject: Double,
        priority: String,
        stateProeject: String,
        progressProject: Int,
        products: List<Purchase>
    ): EmptyResult<DataError.Network>

    suspend fun getProjectByUserId(): ResultExt<List<Project>, DataError.Network>

    suspend fun closeProject(
        projectId: Int
    ): ResultExt<Project, DataError.Network>

    suspend fun getProjectByCustomerId(customerId: String): ResultExt<List<Project>, DataError.Network>

    suspend fun deleteProject(projectId: Int, motivos: String, competencia: String, comments: String): EmptyResult<DataError.Network>
}