package org.propapel.prospeccion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.SessionStorage

class MainViewModel(
    private val sessionStorage: SessionStorage,
    //private val updateAppRepository: UpdateAppRepository
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set
    init {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(
                isLoggedIn = sessionStorage.get() != null,
                isManager = sessionStorage.get()?.roles?.contains("Gerente Regional") ?: false && (sessionStorage.get()?.sucursales?.size ?: 0) > 1
            )
            state = state.copy(isCheckingAuth = false)
        }
    }
    /*
        private val _downloadProgress = MutableStateFlow(0)
    val downloadProgress: StateFlow<Int> = _downloadProgress

    private val _isUpdateAvailable = MutableStateFlow(false)
    val isUpdateAvailable: StateFlow<Boolean> = _isUpdateAvailable

    private var apkFile: File? = null

    fun checkForUpdate(currentVersion: String) {
        viewModelScope.launch {

        }
    }

    private fun downloadApk(url: String) {
        viewModelScope.launch {
            apkFile = updateAppRepository.downloadApk(url) { progress ->
                _downloadProgress.value = progress
            }
        }
    }

    fun getApkFile() = apkFile
     */


}