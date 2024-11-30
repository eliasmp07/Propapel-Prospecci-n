package org.propapel.prospeccion.root.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.selectSucursal.domain.model.UserItem
import org.propapel.prospeccion.selectSucursal.domain.repository.UserRepository

class UserSMViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private var _state = MutableStateFlow(UsersSMState())
    val state: StateFlow<UsersSMState> get() = _state.asStateFlow()

    fun getUserBySucursal(id: Int){
        viewModelScope.launch(
            Dispatchers.IO
        ){
            val result = userRepository.getAllUsersBySucursal(id)
            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            users = listOf(UserItem())
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            users = result.data
                        )
                    }
                }
            }
        }
    }

}

data class UsersSMState(
    val users:List<UserItem> = emptyList()
)