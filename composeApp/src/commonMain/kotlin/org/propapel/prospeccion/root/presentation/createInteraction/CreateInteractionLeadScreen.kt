@file:OptIn(ExperimentalAnimationApi::class,
            ExperimentalMaterial3WindowSizeClassApi::class
)

package org.propapel.prospeccion.root.presentation.createInteraction

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.propapel.prospeccion.root.presentation.createInteraction.components.AddInfoInteractionClient
import org.propapel.prospeccion.root.presentation.createInteraction.components.AddInfoProductsInteresedClient
import org.propapel.prospeccion.root.presentation.createInteraction.components.HeIsInterestedProduct
import org.propapel.prospeccion.root.presentation.dashboard.isMobile

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
    val widthSizeClass = calculateWindowSizeClass()
    Column(
        modifier = Modifier.fillMaxSize().animateContentSize().background(
            Color.White
        )
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            transitionSpec = {
                (fadeIn(

                ) + slideInVertically(animationSpec = tween(600),
                                      initialOffsetY = { fullHeight -> fullHeight })).togetherWith(fadeOut(animationSpec = tween(400)))
            },
            targetState = state.screenState,
            label = "container transform",
        ) { stateScreen ->
            when (stateScreen) {
                CreateInteractionScreenState.InfoInteractionScreen -> {
                    AddInfoInteractionClient(
                        state = state,
                        isMobile = widthSizeClass.isMobile,
                        onAction = onAction
                    )
                }
                CreateInteractionScreenState.He_Client_Intersed_A_Product -> {
                    HeIsInterestedProduct(
                        state = state,
                        isMobile = widthSizeClass.isMobile,
                        onAction = onAction
                    )
                }
                CreateInteractionScreenState.Add_Products -> {
                    AddInfoProductsInteresedClient(
                        state = state,
                        isMobile = widthSizeClass.isMobile,
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