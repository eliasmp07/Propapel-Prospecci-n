@file:OptIn(ExperimentalAnimationApi::class)

package org.propapel.prospeccion.root.presentation.completeReminder

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import org.propapel.prospeccion.root.presentation.completeReminder.components.AddInfoInteresedProductsReminder
import org.propapel.prospeccion.root.presentation.completeReminder.components.AddInfoInterviewReminder
import org.propapel.prospeccion.root.presentation.completeReminder.components.CompleteReminderScreenState
import org.propapel.prospeccion.root.presentation.completeReminder.components.HeIsInterestedInAProductReminder

@Composable
fun CompleteReminderScreenRoot(
    viewModel: CompleteReminderViewModel
) {
    val state by viewModel.state.collectAsState()
    CompleteReminderScreen(
        state = state,
        onAction = {action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun CompleteReminderScreen(
    state: CompleteReminderState,
    onAction: (CompleteReminderAction) -> Unit
) {

    val focusManager = LocalFocusManager.current

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
                CompleteReminderScreenState.ADD_INFO_INTERVIEW -> {
                    AddInfoInterviewReminder(
                        state = state,
                        onAction = onAction
                    )
                }
                CompleteReminderScreenState.HE_IS_INTERESTED_A_PRODUCT -> {
                    HeIsInterestedInAProductReminder(
                        state = state,
                        onAction = onAction
                    )
                }
                CompleteReminderScreenState.ADD_INFO_PRODUCTS_INTERESTED -> {
                    AddInfoInteresedProductsReminder(
                        state = state,
                        onAction = onAction,
                        focusManager = focusManager
                    )
                }
            }
        }
    }
}