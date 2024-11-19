package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.core.data.networking.delete
import org.propapel.prospeccion.core.data.networking.get
import org.propapel.prospeccion.core.data.networking.post
import org.propapel.prospeccion.core.domain.EmptyResult
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.asEmptyDataResult
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.project.CreateProjectRequest
import org.propapel.prospeccion.root.data.dto.project.DeleteProjectRequest
import org.propapel.prospeccion.root.data.dto.project.ProjectResponse
import org.propapel.prospeccion.root.data.mappers.toProject
import org.propapel.prospeccion.root.data.mappers.toPurchasesWithAmountUpdate
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.domain.repository.ProjectRepository

class ProjectRepositoryImpl(private val httpClient: HttpClient) : ProjectRepository {
    override suspend fun createProject(
        nameProject: String,
        customerId: String,
        totalProject: Double,
        priority: String,
        stateProeject: String,
        progressProject: Int,
        products: List<Purchase>
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<CreateProjectRequest, Unit>(
            route = "/projects/create",
            body = CreateProjectRequest(
                nameProject = nameProject,
                customerId = customerId,
                state = stateProeject,
                prioridad = priority,
                progress = progressProject,
                purchases = products.map {
                    it.toPurchasesWithAmountUpdate()
                },
                valorProject = totalProject
            )
        )


        return result.asEmptyDataResult()
    }

    override suspend fun getProjectByCustomerId(customerId: String): ResultExt<List<Project>, DataError.Network> {
        val result = httpClient.get<List<ProjectResponse>>(
            route = "/projects/findProjectById/$customerId"
        )

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(result.data.map {
                    it.toProject()
                })
            }
        }
    }

    override suspend fun deleteProject(
        projectId: Int,
        motivos: String,
        competencia: String,
        comments: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.delete<DeleteProjectRequest, Unit>(
            route = "/projects/delete/$projectId",
            body = DeleteProjectRequest(
                motivos = motivos,
                comments = comments,
                competencia = competencia
            )
        )

        return result.asEmptyDataResult()
    }
}