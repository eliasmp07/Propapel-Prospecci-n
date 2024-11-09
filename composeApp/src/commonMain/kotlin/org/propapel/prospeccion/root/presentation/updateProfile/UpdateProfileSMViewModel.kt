package org.propapel.prospeccion.root.presentation.updateProfile

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
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.domain.repository.ProfileRepository

class UpdateProfileSMViewModel(
    private val sessionStorage: SessionStorage,
    private val profileRepository: ProfileRepository
): ViewModel() {
    private var _state = MutableStateFlow(UpdateProfileSMState())
    val state: StateFlow<UpdateProfileSMState> get() = _state.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO){
            _state.update {
                it.copy(
                    user = sessionStorage.get()?: AuthInfo()
                )
            }
        }
    }

    fun onAction(
        action: UpdateProfileSMAction
    ){
        when(action){
            UpdateProfileSMAction.OnUpdateClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update { it.copy(isLoading = true) }
                    val result = profileRepository.updateProfile(
                        name = state.value.user.name,
                        lastname = state.value.user.lastname,
                        image = state.value.image,
                        phone = "99929393"
                    )
                    _state.update { it.copy(isLoading = false) }
                    when(result){
                        is ResultExt.Error -> {
                            when(result.error){
                                DataError.Network.NO_INTERNET-> {
                                    _state.update {
                                        it.copy(
                                            isError = true,
                                            error = "Contraseña incorrecta"
                                        )
                                    }
                                }
                                DataError.Network.UNAUTHORIZED -> {
                                    _state.update {
                                        it.copy(
                                            isError = true,
                                            error = "El correo electronico no existe"
                                        )
                                    }
                                }
                                else -> {
                                    _state.update {
                                        it.copy(
                                            isError = true,
                                            error = "Error desconocido"
                                        )
                                    }
                                }
                            }
                        }
                        is ResultExt.Success -> {
                            _state.update {
                                it.copy(
                                    user = result.data
                                )
                            }
                            _state.update { it.copy(isSuccess = true, success = "Perfil actualizado") }
                        }
                    }
                }
            }

            UpdateProfileSMAction.HideError -> {
                _state.update {
                    it.copy(
                        isError = !it.isError
                    )
                }
            }
            UpdateProfileSMAction.HideSuccess -> {
                _state.update {
                    it.copy(
                        isSuccess = !it.isSuccess
                    )
                }}
            is UpdateProfileSMAction.OnChangeImageProfile -> {
                _state.update {
                    it.copy(
                        image = action.image
                    )
                }
            }
            is UpdateProfileSMAction.OnChangeLastName -> {
                _state.update {
                    it.copy(
                        user = it.user.copy(
                            lastname = action.lastName
                        )
                    )
                }
            }
            is UpdateProfileSMAction.OnChangeName -> {
                _state.update {
                    it.copy(
                        user = it.user.copy(
                            name = action.name
                        )
                    )
                }
            }
            else -> Unit
        }
    }

}