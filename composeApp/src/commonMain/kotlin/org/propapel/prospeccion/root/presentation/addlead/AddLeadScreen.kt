@file:OptIn(ExperimentalAnimationApi::class)

package org.propapel.prospeccion.root.presentation.addlead

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.root.presentation.addlead.components.AddClientDataScreen
import org.propapel.prospeccion.root.presentation.addlead.components.AddInfoInteresedProductsScreen
import org.propapel.prospeccion.root.presentation.addlead.components.AddInfoRemiderAppointmentScreen
import org.propapel.prospeccion.root.presentation.addlead.components.AddNewDateClientScreen
import org.propapel.prospeccion.root.presentation.addlead.components.ErrorCreateScreen
import org.propapel.prospeccion.root.presentation.addlead.components.HadDateWithClientScreen
import org.propapel.prospeccion.root.presentation.addlead.components.HeIsInterestedInAProduct
import org.propapel.prospeccion.root.presentation.addlead.components.AddMoreInfoLead
import org.propapel.prospeccion.root.presentation.addlead.components.WantBookAAppointScreen
import org.propapel.prospeccion.root.presentation.addlead.components.utils.SuccessCreateScreen
import org.propapel.prospeccion.root.presentation.createReminder.components.DialogDayNoAvailable

@Composable
fun AddLeadScreenRoot(
    viewModel: AddLeadViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    AddLeadScreen(
        state = state,
        onAction = { action ->
            when (action) {
                AddLeadAction.OnBackClick -> {
                    if (state.contactName.isNotEmpty() || state.nameCompany.isNotEmpty()) {
                        viewModel.onAction(AddLeadAction.OnNextScreenClick(ContainerState.FINISH))
                    } else {
                        onBack()
                    }
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


enum class ContainerState {
    ADDLEAD,
    HAD_INTERACTION_CUSTOMER,
    ADD_INTERACTION_CUSTOMER,
    ADD_MORE_INFO_LEAD,
    HE_IS_INTERESTED_IN_A_PRODUCT,
    WANT_BOOK_AN_APPOINTMENT,
    ADD_INFO_INTERESED_CLIENTE,
    ADD_INFO_REMINDER_APPOINTMENT,
    FINISH,
    ISERROR,
    ISSUCCESS
}

@Composable
private fun AddLeadScreen(
    state: AddLeadState,
    onAction: (AddLeadAction) -> Unit
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
                fadeIn(

                ) + slideInVertically(animationSpec = tween(600),
                    initialOffsetY = { fullHeight -> fullHeight }) with
                        fadeOut(animationSpec = tween(400))
            },
            targetState = state.screenState,
            label = "container transform",
        ) { stateScreen ->
            when (stateScreen) {
                ContainerState.ADDLEAD -> {
                    AddClientDataScreen(
                        state = state,
                        onAction = onAction,
                        focusManager = focusManager
                    )
                }
                ContainerState.ADD_MORE_INFO_LEAD -> {
                    AddMoreInfoLead(
                        state = state,
                        onAction = onAction
                    )
                }

                ContainerState.HAD_INTERACTION_CUSTOMER -> {
                    HadDateWithClientScreen(
                        state = state,
                        onAction = onAction
                    )
                }
                ContainerState.ADD_INTERACTION_CUSTOMER -> {
                    AddNewDateClientScreen(
                        state = state,
                        onAction = onAction
                    )
                }

                ContainerState.WANT_BOOK_AN_APPOINTMENT -> {
                    WantBookAAppointScreen(onAction = onAction)
                }

                ContainerState.ADD_INFO_INTERESED_CLIENTE -> {
                    AddInfoInteresedProductsScreen(
                        onAction = onAction,
                        focusManager = focusManager,
                        state = state
                    )
                }

                ContainerState.ADD_INFO_REMINDER_APPOINTMENT -> {
                    AddInfoRemiderAppointmentScreen(
                        state = state,
                        focusManager = focusManager,
                        onAction = onAction
                    )
                }

                ContainerState.HE_IS_INTERESTED_IN_A_PRODUCT -> {
                    HeIsInterestedInAProduct(
                        onAction = onAction,
                        state = state
                    )
                }
                ContainerState.FINISH -> {
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LoadingPropapel()
                    }
                }

                ContainerState.ISERROR -> {
                    ErrorCreateScreen(onAction)
                }

                ContainerState.ISSUCCESS -> {
                    SuccessCreateScreen(
                        onAction = onAction
                    )
                }
            }
        }
    }
    if (state.showAvailableDayDialog) {
        DialogDayNoAvailable {
            onAction(AddLeadAction.OnToggleDateNoAvailable)
        }
    }

}