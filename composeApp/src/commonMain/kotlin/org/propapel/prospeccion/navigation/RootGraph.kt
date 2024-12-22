package org.propapel.prospeccion.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import org.propapel.prospeccion.navigation.utils.Destination

@Composable
fun RootGraph(
    isLogging: Boolean = false,
    isManager: Boolean = false,
) {


    var isSuccess by remember {
        mutableStateOf(false)
    }

    var messageSuccess by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = isSuccess) {
        if (isSuccess) {
            delay(2000)
            isSuccess = false
        }
    }
    val mainNavigation = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = mainNavigation,
            startDestination = if (isManager) Destination.Gerente else if (isLogging) Destination.ProSales else Destination.AuthGraph
        ) {
            auth(
                navController = mainNavigation,
                onSuccess = {
                    messageSuccess = it
                    isSuccess = true
                }
            )
            gerente(
                navController = mainNavigation
            )
            proSales(navController = mainNavigation)
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = isSuccess,
            enter = slideInVertically(
                initialOffsetY = { it } // Aparece de abajo hacia arriba
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it } // Desaparece de arriba hacia abajo
            ) + fadeOut(),
        ) {
            ElevatedCard(
                modifier = Modifier.padding(10.dp).align(Alignment.BottomCenter),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFF46D19E)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = messageSuccess,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }


}
