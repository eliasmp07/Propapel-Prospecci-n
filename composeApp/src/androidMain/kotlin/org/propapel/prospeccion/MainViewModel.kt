package org.propapel.prospeccion

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.domain.Downloader
import org.propapel.prospeccion.dowloadapk.DownloadApk
import org.propapel.prospeccion.updateApk.UpdateAppRepository
import java.io.File

class MainViewModel(
    private val sessionStorage: SessionStorage,
    private val updateAppRepository: UpdateAppRepository,
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    private val _blockVersion = MutableStateFlow<Boolean>(false)
    val blockVersion: StateFlow<Boolean> = _blockVersion

    private val _linkVersion = MutableStateFlow("")
    val linkVersion: StateFlow<String> get() = _linkVersion

    private val _newVersion = MutableStateFlow("")
    val newVersion: StateFlow<String> get() = _newVersion

    init {
        canAccessApp()
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(
                isLoggedIn = sessionStorage.get() != null,
                isManager = sessionStorage.get()?.roles?.contains("Gerente Regional") ?: false && (sessionStorage.get()?.sucursales?.size ?: 0) > 1
            )
            state = state.copy(isCheckingAuth = false)
        }
    }


    private fun canAccessApp(){
        viewModelScope.launch {
            val currentVersion = updateAppRepository.getCurrentVersion() //1.0.3
            val minAllowedVersion = updateAppRepository.getMinAllowedVersion() //1.0.2
            val version = updateAppRepository.getApkLink()

            _linkVersion.value = version
            _newVersion.value = minAllowedVersion.toString()

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