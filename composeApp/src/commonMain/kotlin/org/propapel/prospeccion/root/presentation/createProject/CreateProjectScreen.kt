@file:OptIn(ExperimentalAnimationApi::class)

package org.propapel.prospeccion.root.presentation.createProject

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState.ADD_INFO_PROJECT
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState.CONFIRM_PROJECT
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState.ERROR_CREATE
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState.PRODUCTS_CHANGE
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState.SUCCESS_CREATE
import org.propapel.prospeccion.root.presentation.createProject.CreateProjectScreenState.WELCOME
import org.propapel.prospeccion.root.presentation.createProject.componetns.AddInfoProject
import org.propapel.prospeccion.root.presentation.createProject.componetns.AddProductsProject
import org.propapel.prospeccion.root.presentation.createProject.componetns.ConfirmProjectIsCorrect
import org.propapel.prospeccion.root.presentation.createProject.componetns.ErrorCreateProject
import org.propapel.prospeccion.root.presentation.createProject.componetns.SuccessCreateProject
import org.propapel.prospeccion.root.presentation.createProject.componetns.WelcomeScreenProject

@Composable
fun CreateProjectScreenRoot(
    viewModel: CreateProjectViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    CreateProjectScreen(
        state = state,
        onAction = { action ->
            when(action){
                CreateProjectAction.OnBackClick -> onBack()
                else -> Unit
            }
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
                (fadeIn() + slideInVertically(animationSpec = tween(400),
                                              initialOffsetY = { fullHeight -> fullHeight })).togetherWith(fadeOut(animationSpec = tween(200)))
            }
        ){ tarjetState ->
            when(tarjetState){
                WELCOME -> {
                    WelcomeScreenProject(
                        state = state,
                        onAction = onAction
                    )
                }
                ADD_INFO_PROJECT -> {
                    AddInfoProject(
                        state = state,
                        onAction = onAction
                    )
                }
                PRODUCTS_CHANGE -> {
                    AddProductsProject(
                        state = state,
                        onAction = onAction
                    )
                }
                CONFIRM_PROJECT -> {
                    ConfirmProjectIsCorrect(
                        state = state,
                        onAction = onAction
                    )
                }
                SUCCESS_CREATE -> {
                    SuccessCreateProject(
                        state = state,
                        onAction = onAction
                    )
                }
                ERROR_CREATE -> {
                    ErrorCreateProject(
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
    CONFIRM_PROJECT,
    SUCCESS_CREATE,
    ERROR_CREATE
}