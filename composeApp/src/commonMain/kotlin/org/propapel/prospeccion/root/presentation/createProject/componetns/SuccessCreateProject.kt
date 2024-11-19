package org.propapel.prospeccion.root.presentation.createProject.componetns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiWhite
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectAction
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectState

@Composable
fun SuccessCreateProject(
    state: CreateProjectState,
    onAction: (CreateProjectAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(SuccessGreen),
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End).padding(16.dp),
            onClick = {
                onAction(CreateProjectAction.OnBackClick)
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }
        )
        Spacer(
            modifier = Modifier.weight(0.2f)
        )
        KottieAnimationUtil(
            modifier = Modifier.size(350.dp).align(Alignment.CenterHorizontally),
            fileRoute = "files/anim_success_create_project.json"
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Proyecto creado con exito",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = SoporteSaiWhite
        )
        Spacer(
            modifier = Modifier.weight(0.5f)
        )
        ProSalesActionButtonOutline(
            modifier = Modifier.padding(16.dp),
            borderColor = Color.White,
            textColor = Color.White,
            text = "Aceptar",
            onClick = {
                onAction(CreateProjectAction.OnBackClick)
            }
        )
    }
}