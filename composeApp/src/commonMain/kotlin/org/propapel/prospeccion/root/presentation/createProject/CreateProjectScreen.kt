@file:OptIn(ExperimentalAnimationApi::class)

package org.propapel.prospeccion.root.presentation.createProject

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.propapel.prospeccion.root.presentation.createProject.componetns.AddInfoProject
import org.propapel.prospeccion.root.presentation.createProject.componetns.AddProductsProject
import org.propapel.prospeccion.root.presentation.createProject.componetns.ConfirmProjectIsCorrect
import org.propapel.prospeccion.root.presentation.createProject.componetns.WelcomeScreenProject

@Composable
fun CreateProjectScreenRoot(
    viewModel: CreateProjectViewModel
) {
    val state by viewModel.state.collectAsState()
    CreateProjectScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun CreateProjectScreen(
    state: CreateProjectState,
    onAction: (CreateProjectAction) -> Unit
) {
    Column {
        AnimatedContent(
            targetState = state.stateScreen,
            transitionSpec = {
                fadeIn() + slideInVertically(animationSpec = tween(400),
                                             initialOffsetY = { fullHeight -> fullHeight }) with
                        fadeOut(animationSpec = tween(200))
            }
        ){ tarjetState ->
            when(tarjetState){
                CreateProjectScreenState.WELCOME -> {
                    WelcomeScreenProject(
                        state = state,
                        onAction = onAction
                    )
                }
                CreateProjectScreenState.ADD_INFO_PROJECT -> {
                    AddInfoProject(
                        state = state,
                        onAction = onAction
                    )
                }
                CreateProjectScreenState.PRODUCTS_CHANGE -> {
                    AddProductsProject(
                        state = state,
                        onAction = onAction
                    )
                }
                CreateProjectScreenState.CONFIRM_PROJECT -> {
                    ConfirmProjectIsCorrect(
                        state = state,
                        onAction = onAction
                    )
                }
            }

        }
    }
}

enum class CreateProjectScreenState {
    WELCOME,
    ADD_INFO_PROJECT,
    PRODUCTS_CHANGE,
    CONFIRM_PROJECT
}