package org.propapel.prospeccion.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.auth.domain.AuthRepository
import org.propapel.prospeccion.auth.domain.validator.UserValidator
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.core.presentation.ui.asUiText
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.error_no_account
import prospeccion.composeapp.generated.resources.error_password_incorrect

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private var _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state.asStateFlow()

    fun onAction(
        action: LoginAction
    ){
        when(action){
            is LoginAction.OnEmailChange -> {
                _state.update {
                    it.copy(email = action.email)
                }
            }
            LoginAction.OnLoginClick -> onLogin()
            is LoginAction.OnPasswordChange -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }

            LoginAction.OnDismissDialogErrorClick -> {
                _state.update { it.copy(isError = !it.isError) }
            }

            LoginAction.HideError -> {
                _state.update {
                    it.copy(isError = !it.isError)
                }
            }
        }
    }

    private fun onLogin() {
        val canLogging = validateAndSetErrors()
        if (!canLogging){
            viewModelScope.launch(Dispatchers.IO) {
                _state.update { it.copy(isLogging = true) }
                val result = authRepository.login(
                    email = _state.value.email,
                    password = _state.value.password
                )
                _state.update { it.copy(isLogging = false) }
                when(result) {
                    is ResultExt.Error -> {
                        when(result.error){
                            DataError.Network.FORBIDDEN -> {
                                _state.update {
                                    it.copy(
                                        isError = true,
                                        error = UiText.StringResource(Res.string.error_password_incorrect)//"Contraseña incorrecta"
                                    )
                                }
                            }
                            DataError.Network.NOT_FOUND -> {
                                _state.update {
                                    it.copy(
                                        isError = true,
                                        error = UiText.StringResource(Res.string.error_no_account)//"El correo electronico no existe"
                                    )
                                }
                            }
                            else -> {
                                _state.update {
                                    it.copy(
                                        isError = true,
                                        error = result.error.asUiText()
                                    )
                                }
                            }
                        }
                    }
                    is ResultExt.Success -> {
                        _state.update {
                            it.copy(
                                loginSuccess = true
                            )
                        }
                    }
                }
            }
        }
    }

    //Funcion que verifica si puede inicar sesión
    private fun validateAndSetErrors(): Boolean {
        val emailError = validErrorEmail(_state.value.email)
        val passwordError = validPassword(_state.value.password)
        _state.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }
        return emailError != null || passwordError != null
    }


    private fun validErrorEmail(email: String): String?{
        return when {
            email.isEmpty() -> "El campo esta vacio"
            !UserValidator.isEmailValid(email) -> "Ingrese una correo valido"
            else -> null
        }
    }


    private fun validPassword(password: String): String? {
        return when {
            password.isEmpty() -> "El campo esta vacio"
            else -> null
        }
    }

}