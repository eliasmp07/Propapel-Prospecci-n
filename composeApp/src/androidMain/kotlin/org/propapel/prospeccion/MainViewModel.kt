package org.propapel.prospeccion

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.updateApk.UpdateAppRepository
import java.io.File

class MainViewModel(
    private val sessionStorage: SessionStorage,
    private val updateAppRepository: UpdateAppRepository
    //private val updateAppRepository: UpdateAppRepository
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    private val _blockVersion = MutableStateFlow<Boolean>(false)
    val blockVersion: StateFlow<Boolean> = _blockVersion

    private val _downloadProgress = MutableStateFlow(0)
    val downloadProgress: StateFlow<Int> = _downloadProgress

   var apkFile: File? = null

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


    fun downloadApk() {
        viewModelScope.launch {
            apkFile = updateAppRepository.downloadApk { progress ->
                _downloadProgress.value = progress
            }
        }
    }

    private fun canAccessApp(){
        viewModelScope.launch {
            val currentVersion = updateAppRepository.getCurrentVersion() //1.0.3
            val minAllowedVersion = updateAppRepository.getMinAllowedVersion() //1.0.2

            for((currentPart, minVersionPart) in currentVersion.zip(minAllowedVersion)){
                if(currentPart!=minVersionPart){
                    Log.i("UPDATE APP", _blockVersion.toString())
                    _blockVersion.value =  currentPart > minVersionPart
                    Log.i("UPDATE APP", _blockVersion.toString())

                }
            }
        }
    }

}