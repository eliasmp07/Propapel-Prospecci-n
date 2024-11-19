package org.propapel.prospeccion.root.presentation.projects

import org.propapel.prospeccion.root.domain.models.Project

data class ProjectsScreenState(
    val projects: List<Project> = listOf()
)
