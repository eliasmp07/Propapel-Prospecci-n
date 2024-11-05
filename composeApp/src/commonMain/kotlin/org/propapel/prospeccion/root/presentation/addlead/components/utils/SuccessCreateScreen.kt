@file:OptIn(ExperimentalResourceApi::class)

package org.propapel.prospeccion.root.presentation.addlead.components.utils

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiWhite
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction

@Composable
fun SuccessCreateScreen(
    onAction: (AddLeadAction) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize().background(SuccessGreen),
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End).padding(16.dp),
            onClick = {
                onAction(AddLeadAction.OnBackClick)
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
            fileRoute = "files/anim_success_customer_create.json"
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Cliente creado con exito",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = SoporteSaiWhite
        )
        Spacer(
            modifier = Modifier.weight(0.5f)
        )
    }
}