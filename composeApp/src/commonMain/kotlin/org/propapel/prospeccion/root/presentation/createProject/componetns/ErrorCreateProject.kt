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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiWhite
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectAction
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectState

@Composable
fun ErrorCreateProject(
    state: CreateProjectState,
    onAction: (CreateProjectAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.error),
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End).padding(16.dp),
            onClick = {
                onAction(CreateProjectAction.OnBackClick)
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
        Spacer(
            modifier = Modifier.weight(0.2f)
        )
        KottieAnimationUtil(
            modifier = Modifier.size(350.dp).align(Alignment.CenterHorizontally),
            fileRoute = "files/anim_error.json"
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
            text = "Hubo un error al create el proyecto",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = SoporteSaiWhite
        )
        Spacer(
            modifier = Modifier.weight(0.5f)
        )
    }
}