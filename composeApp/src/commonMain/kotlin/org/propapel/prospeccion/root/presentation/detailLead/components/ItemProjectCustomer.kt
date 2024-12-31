package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import contentScale.ContentScale
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellow
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateAttention
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterBottom
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.leads.components.mobile.loadProgress
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.ic_alert_average
import prospeccion.composeapp.generated.resources.ic_alert_high
import prospeccion.composeapp.generated.resources.ic_alert_low
import prospeccion.composeapp.generated.resources.project_confirm

@Composable
fun ItemProjectCustomer(
    project: Project,
    onDelete: (Project) -> Unit
){
    var showProducts by remember {
        mutableStateOf(false)
    }

    var finishLoad by remember {
        mutableStateOf(false)
    }
    var currentProgress by remember { mutableStateOf(0f) }
    val color = remember {
        Animatable(Color.White)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        loadProgress(
            value = project.progress.toFloat(),
            updateColor = {
                scope.launch {
                    color.animateTo(
                        it,
                        animationSpec = tween(200)
                    )
                }
            }
        ) {
            currentProgress = it
        }
        finishLoad = true
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth().animateContentSize().padding(4.dp),
        shape = RoundedCornerShape(
            20.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ){
        Column(
            modifier = Modifier.fillMaxWidth().background(
                Color.White
            ).animateContentSize()
        ){
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)).background(
                    brush = Brush.verticalGradient(
                        listOf(
                            PrimaryYellowLight,
                            PrimaryYellow
                        )
                    )
                ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Proyecto: ${project.nameProject}",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).pointerHoverIcon(PointerIcon.Hand),
                    onClick = {
                        onDelete(project)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null
                        )
                    }
                )

            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    modifier = Modifier.size(100.dp).padding(8.dp),
                    painter = painterResource(Res.drawable.project_confirm ),
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                Column {
                    Text(
                        text = if (project.status.contains("Cierre")) "Proyecto cerrado" else "Progreso",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().height(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LinearProgressIndicator(
                            progress = { currentProgress },
                            color = color.value,
                            modifier = Modifier.weight(0.9f).height(10.dp),
                        )

                        if ((currentProgress * 100) >= 100.0) {
                            AnimatedVisibility(
                                visible = finishLoad,
                                enter = scaleIn(
                                    animationSpec = tween(
                                        500,
                                        delayMillis = 500
                                    )
                                ) + fadeIn()
                            ) {

                                Box(modifier = Modifier.weight(0.1f), contentAlignment = Alignment.Center) {
                                    KottieAnimationUtil(
                                        fileRoute = "files/sucess_lead_meta.json",
                                        modifier = Modifier.size(60.dp),
                                        interations = 3,
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        modifier = Modifier.animateAttention(),
                                        text = "${currentProgress * 100}%",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                            }
                        } else {
                            AnimatedVisibility(
                                visible = finishLoad,
                                enter = scaleIn()
                            ) {
                                Text(
                                    modifier = Modifier.weight(0.1f),
                                    text = "${currentProgress * 100}%",
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }

                    }
                    Text(
                        text = "$${project.valorProject}MXN",
                        style = MaterialTheme.typography.titleSmall,

                        color = Color.Gray
                    )
                    Text(
                        text = "Prioridad: ${project.prioridad}",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                    FilterChip(
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            showProducts = !showProducts
                        },
                        selected = showProducts,
                        label = {
                            Text(
                                text = if (showProducts) "Ocultar productos" else "Ver productos (${project.products.size})"
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = if (showProducts) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    )
                }
            }
            if (showProducts){
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Productos",
                    style = MaterialTheme.typography.titleSmall
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if(project.products.isNotEmpty()){
                        items(project.products){
                            ItemProduct(
                                purchase = it
                            )
                        }
                    }else{
                        item {
                            Text(
                                text = "Este proyecto no tiene productos",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun priorityStatus(status:String): ImageVector{
    return when(status){
        "Alta" -> vectorResource(Res.drawable.ic_alert_high)
        "Medio" ->  vectorResource(Res.drawable.ic_alert_average)
        else ->  vectorResource(Res.drawable.ic_alert_low)
    }
}


fun progressColor(valor: Int,status: String): Color {
    return when {
        valor == 100 || status.contains("Cierre") -> SuccessGreen
        valor in 50..99 -> SoporteSaiBlue
        valor in 25..49 -> Color(0xFFFFA500)
        else -> Color.Red
    }
}
