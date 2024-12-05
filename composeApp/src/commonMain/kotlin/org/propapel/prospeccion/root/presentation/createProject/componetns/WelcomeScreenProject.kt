package org.propapel.prospeccion.root.presentation.createProject.componetns

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectAction
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.background_intro
import prospeccion.composeapp.generated.resources.create_project
import prospeccion.composeapp.generated.resources.create_project_img
import prospeccion.composeapp.generated.resources.project_confirm

@Composable
fun WelcomeScreenProject(
    state: CreateProjectState,
    onAction: (CreateProjectAction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        )
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.TopStart).padding(16.dp),
            onClick = {
                onAction(CreateProjectAction.OnBackClick)
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = null
                )
            }
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.fillMaxWidth().height(400.dp).offset(y = 10.dp, x = 20.dp),
                painter = painterResource(Res.drawable.create_project_img),
                contentDescription = null
            )
            ElevatedCard(
                shape = RoundedCornerShape(
                    topEnd = 30.dp,
                    topStart = 30.dp
                ),
                content = {
                    Column(
                        modifier = Modifier.background(Color.White).fillMaxWidth().padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        // Título principal
                        Text(
                            text = "¡Bienvenido a la creación de proyectos!",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(
                            modifier = Modifier.height(32.dp)
                        )
                        // Botón de acción
                        ProSalesActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            textColor = Color.White,
                            text = "Empezar a crear",
                            onClick = {
                                onAction(CreateProjectAction.OnNextScreen(CreateProjectScreenState.ADD_INFO_PROJECT))
                            }
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        ProSalesActionButtonOutline(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            text = "Salir",
                            onClick = {
                                onAction(CreateProjectAction.OnBackClick)
                            }
                        )
                    }
                }
            )

        }
    }
}
