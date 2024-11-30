package org.propapel.prospeccion.root.presentation.createProject.componetns

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectAction
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectState

@Composable
fun AddInfoProject(
    state: CreateProjectState,
    onAction: (CreateProjectAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        ProSalesTextField(
            title = "Nombre del proyecto",
            state = state.nameProject,
            onTextChange = {
                onAction(CreateProjectAction.OnChangeNameProject(it))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        var expandedPrioridad by remember {
            mutableStateOf(false)
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        ExposedDropdownMenuGereric(
            title = "Prioridad",
            state = expandedPrioridad,
            colors = Color.Black,
            onDimiss = {
                expandedPrioridad = !expandedPrioridad
            },
            optionSelectable = state.priorityProject,
            listOptions = listOf("Alta", "Media", "Baja"),
            content = {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    leadingIcon = {
                        Icon(
                            imageVector =  when(it){
                                "Alta" -> Icons.Filled.PriorityHigh
                                "Media" -> Icons.Filled.Start
                                else -> Icons.Filled.Bookmark
                            },
                            contentDescription = null
                        )
                    },
                    onClick = {
                        expandedPrioridad = !expandedPrioridad
                        onAction(CreateProjectAction.OnChangePriotityProject(it))
                    }
                )
            }
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )
        ButtonsContinueCreateProjectScreen(
            onPreview = {
                onAction(CreateProjectAction.OnNextScreen(CreateProjectScreenState.WELCOME))
            },
            onNext = {
                onAction(CreateProjectAction.OnNextScreen(CreateProjectScreenState.PRODUCTS_CHANGE))
            }
        )
    }
}


@Composable
fun ButtonsContinueCreateProjectScreen(
    modifier: Modifier = Modifier,
    onPreview: () -> Unit,
    onNext: () -> Unit
){
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        ProSalesActionButtonOutline(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f),
            text = "Volver",
            onClick = {
                onPreview()
            }
        )
        Spacer(modifier = Modifier.weight(0.2f))
        ProSalesActionButton(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f),
            textColor = Color.White,
            text = "Continuar",
            onClick = {
                onNext()
            }
        )
    }
}