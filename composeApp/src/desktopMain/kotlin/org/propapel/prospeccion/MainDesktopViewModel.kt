package org.propapel.prospeccion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.SessionStorage

class MainDesktopViewModel (
    private val sessionStorage: SessionStorage
) : ViewModel() {

    var state by mutableStateOf(MainDesktopState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(
                isLoggedIn = sessionStorage.get() != null,
                isManager = sessionStorage.get()?.roles?.contains("Gerente") ?: false && (sessionStorage.get()?.sucursales?.size ?: 0) > 1,
                isRegional = sessionStorage.get()?.roles?.contains("Gerente Regional") ?: false
            )
            state = state.copy(isCheckingAuth = false)
        }
    }
}