package org.propapel.prospeccion.root.presentation.createProject.componetns

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectAction
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.project_confirm

@Composable
fun WelcomeScreenProject(
    state: CreateProjectState,
    onAction: (CreateProjectAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            // Título principal
            Text(
                text = "¡Bienvenido a la creación de proyectos!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Image(
                modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally),
                painter = painterResource(Res.drawable.project_confirm),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(32.dp))


            // Descripción del propósito
            Text(
                text = "Estás a punto de comenzar la creación de un proyecto para satisfacer las necesidades de este cliente. Completa los detalles en los próximos pasos para crear el proyecto correctamente.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(end = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón de acción
            ProSalesActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textColor = Color.White,
                text = "Empezar a crear",
                onClick = {
                    onAction(CreateProjectAction.OnNextScreen(CreateProjectScreenState.ADD_INFO_PROJECT))
                }
            )
        }
    }
}
