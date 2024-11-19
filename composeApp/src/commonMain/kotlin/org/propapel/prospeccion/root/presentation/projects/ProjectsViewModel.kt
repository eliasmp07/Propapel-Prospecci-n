package org.propapel.prospeccion.root.presentation.projects

import androidx.lifecycle.ViewModel
import org.propapel.prospeccion.root.domain.repository.ProjectRepository

class ProjectsViewModel(
    private val projectRepository: ProjectRepository
): ViewModel() {
}