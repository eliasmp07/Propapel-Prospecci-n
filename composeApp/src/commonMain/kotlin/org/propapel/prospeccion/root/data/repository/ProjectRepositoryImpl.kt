package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.root.domain.repository.ProjectRepository

class ProjectRepositoryImpl(private val httpClient: HttpClient) : ProjectRepository {
}