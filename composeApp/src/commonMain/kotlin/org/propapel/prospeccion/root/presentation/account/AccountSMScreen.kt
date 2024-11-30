package org.propapel.prospeccion.root.presentation.account

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.propapel.prospeccion.root.presentation.account.components.desktop.AccountScreenDesktop
import org.propapel.prospeccion.root.presentation.account.components.mobile.AccountScreenMobile
import org.propapel.prospeccion.root.presentation.dashboard.isMobile

@Composable
fun AccountScreenRoot(
    viewModel: AccountSMViewModel,
    windowSizeClass: WindowSizeClass,
    onAction: (AccountSMAction) -> Unit
){
    val state by viewModel.state.collectAsState()
    AccountScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onAction = onAction
    )
}

@Composable
private fun AccountScreen(
    state: AccountSMState,
    windowSizeClass: WindowSizeClass,
    onAction: (AccountSMAction) -> Unit
){
    if (windowSizeClass.isMobile){
        AccountScreenMobile(
            state = state,
            onAction = onAction
        )
    } else{
        AccountScreenDesktop(
            state = state,
            onAction = onAction
        )
    }
}