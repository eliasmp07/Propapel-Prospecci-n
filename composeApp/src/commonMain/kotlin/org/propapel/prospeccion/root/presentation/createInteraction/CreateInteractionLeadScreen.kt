@file:OptIn(ExperimentalAnimationApi::class)

package org.propapel.prospeccion.root.presentation.createInteraction

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.propapel.prospeccion.root.presentation.createInteraction.components.AddInfoInteractionClient
import org.propapel.prospeccion.root.presentation.createInteraction.components.AddInfoProductsInteresedClient
import org.propapel.prospeccion.root.presentation.createInteraction.components.HeIsInterestedProduct

@Composable
fun  CreateInteractionLeadScreeRoot(
    viewModel: CreateInteractionViewModel,
    onBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSuccessCreate){
        if (state.isSuccessCreate){
            onBack()
        }
    }

    CreateInteractionLeadScreen(
        state = state,
        onAction = { action ->
            when (action) {
                CreateInteractionAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

enum class CreateInteractionScreenState{
    InfoInteractionScreen,
    He_Client_Intersed_A_Product,
    Add_Products,
    FINISH
}
@Composable
private fun  CreateInteractionLeadScreen(
    state: CreateInteractionLeadState,
    onAction: (CreateInteractionAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().animateContentSize().background(
            Color.White
        )
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            transitionSpec = {
                fadeIn(

                ) + slideInVertically(animationSpec = tween(600),
                                      initialOffsetY = { fullHeight -> fullHeight }) with
                        fadeOut(animationSpec = tween(400))
            },
            targetState = state.screenState,
            label = "container transform",
        ) { stateScreen ->
            when (stateScreen) {
                CreateInteractionScreenState.InfoInteractionScreen -> {
                    AddInfoInteractionClient(
                        state = state,
                        onAction = onAction
                    )
                }
                CreateInteractionScreenState.He_Client_Intersed_A_Product -> {
                    HeIsInterestedProduct(
                        state = state,
                        onAction = onAction
                    )
                }
                CreateInteractionScreenState.Add_Products -> {
                    AddInfoProductsInteresedClient(
                        state = state,
                        onAction = onAction
                    )
                }
                CreateInteractionScreenState.FINISH -> {
                    Column {
                        Text("Creado con exito")
                    }
                }
            }
        }
    }
}