package org.propapel.prospeccion.root.presentation.leads.components.mobile

import KottieAnimation
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import contentScale.ContentScale
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateAttention
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateAttentionRepeat
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import prospeccion.composeapp.generated.resources.Res

@Composable
fun ItemLead(
    modifier: Modifier = Modifier,
    customer: Customer,
    shape: RoundedCornerShape = RoundedCornerShape(30.dp),
    onClick: (String) -> Unit
) {
    var finishLoad by remember {
        mutableStateOf(false)
    }
    var currentProgress by remember { mutableStateOf(0f) }
    val color = remember {
        Animatable(Color.White)
    }
    var scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        loadProgress(
            value = customer.progressLead.toFloat(),
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

    Card(
        modifier = modifier.fillMaxWidth().animateContentSize(),
        onClick = {
            onClick(customer.idCustomer.toString())
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = shape
    ) {
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth().animateContentSize()
            ) {
                Text(
                    text = "Empresa",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = customer.companyName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Numero de contacto",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = customer.phoneNumber,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Progreso del lead",
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
            }
        }
    }
}


suspend fun loadProgress(
    value: Float,
    updateColor: (Color) -> Unit,
    updateProgress: (Float) -> Unit
) {
    for (i in 1..value.toInt()) {
        updateProgress(i.toFloat() / 100)
        updateColor(colorProgress(i))
        delay(10)
    }
}

fun colorProgress(progress: Int): Color {
    return when (progress) {
        100 -> SuccessGreen
        in 75..99 -> Color.Yellow
        in 50..74 -> Color(0xFFFF9800)
        in 10..50 -> Color.Red
        else -> Color.Gray
    }
}