package org.propapel.prospeccion.root.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.SessionStorage

class AccountSMViewModel(
    private val sessionStorage: SessionStorage
): ViewModel() {
    private var _state = MutableStateFlow(AccountSMState())
    val state: StateFlow<AccountSMState> get() = _state.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO){
            _state.update {
                it.copy(
                    user = sessionStorage.get()?: AuthInfo()
                )
            }
        }
    }

    fun onAction(action: AccountSMAction){
        when(action){
            AccountSMAction.OnLogoutClick -> {
                viewModelScope.launch(Dispatchers.IO){
                    sessionStorage.set(null)
                }
            }
            else -> Unit
        }
    }
}