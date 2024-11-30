package org.propapel.prospeccion.auth.presentation.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellow
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterRight
import org.propapel.prospeccion.root.presentation.createReminder.components.DialogCreateSuccess
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.foreground
import prospeccion.composeapp.generated.resources.logo

@Composable
fun IntroScreenRoot(
    onLogin: () -> Unit
) {
    IntroScreen(
        onAction = {
            when (it) {
                IntroAction.OnLoginClick -> onLogin()
            }
        }
    )
}

@Composable
private fun IntroScreen(
    onAction: (IntroAction) -> Unit
) {

    var showAboutInfo by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().weight(0.6f).background(
                Brush.verticalGradient(
                    0f to PrimaryYellowLight,
                    0.6f to SoporteSaiBlue30,
                    1f to MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(
                    bottomStart = 100.dp,
                    bottomEnd = 100.dp
                )
            ),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                onClick = {
                    showAboutInfo = true
                },
                modifier = Modifier.align(Alignment.TopEnd).padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Play",
                    tint = Color.Black
                )
            }
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.align(Alignment.TopStart).padding(20.dp)
            )
            Image(
                painter = painterResource(Res.drawable.foreground),
                contentDescription = "Logo",
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomStart = 100.dp,
                        bottomEnd = 100.dp
                    )
                )
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().weight(0.3f).padding(horizontal = 30.dp),
        ) {
            Spacer(
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "Bienvenido a ProSales",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = "Impulsa tus ventas con prospección inteligente",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = "• Encuentra clientes potenciales rápidamente",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "• Organiza tus contactos y oportunidades en un solo lugar",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "• Lleva el seguimiento de tus avances de manera fácil",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(0.1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Text(
                text = "© 2024 Propapel.\n Todos los derechos reservados.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier.animateEnterRight().clip(
                    shape = RoundedCornerShape(
                        topStart = 50.dp,
                    )
                ).clickable {
                    onAction(IntroAction.OnLoginClick)
                }.width(200.dp).shadow(
                    30.dp,
                    shape = RoundedCornerShape(
                        topStart = 50.dp,
                    )
                ).fillMaxHeight().background(
                    color = PrimaryYellow,
                    shape = RoundedCornerShape(
                        topStart = 50.dp,
                    )
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Comenzar",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
    if (showAboutInfo) {
        DialogCreateSuccess(
            image = Res.drawable.logo,
            title = "Acerca de ProSales",
            message = "ProSales es una aplicación de prospección inteligente, diseñada para facilitar la búsqueda de clientes potenciales de manera rápida y efectiva. Con ProSales, puedes organizar tus contactos y oportunidades en un solo lugar y realizar un seguimiento detallado de tus avances. \n \n Esta herramienta está administrada por Propapel Mérida, comprometidos en impulsar tu éxito en ventas.",
            textButton = "Cerrar",
            onDismissRequest = {
                showAboutInfo = false
            }
        )
    }


}