package org.propapel.prospeccion.root.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.presentation.ui.TimeUtils
import org.propapel.prospeccion.core.presentation.ui.toImageAndTextError
import org.propapel.prospeccion.root.domain.repository.BannerRepository
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.InteractionRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository
import org.propapel.prospeccion.root.presentation.leads.UiState
import org.propapel.prospeccion.root.presentation.leads.toState

class DashboardSMViewModel(
    private val reminderRepository: ReminderRepository,
    private val customerRepository: CustomerRepository,
    private val bannerRepository: BannerRepository,
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private var _state = MutableStateFlow(DashboardSMState())
    val state: StateFlow<DashboardSMState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sessionStorage.getUserFlow().collectLatest { user ->
                _state.update {
                    it.copy(
                        user = user?: AuthInfo()
                    )
                }
            }

        }
        getAllBanners()
        getAllMyReminders()
        getMyCustomer()
    }


    fun onAction(action: DashboardSMAction) {
        when (action) {
            DashboardSMAction.OnRetryReminders -> {
                getAllMyReminders()
            }
            DashboardSMAction.OnRetryCustomer -> {
                getMyCustomer()
            }
            is DashboardSMAction.OnDateChange -> {
                _state.update { it.copy(date = action.date) }
            }

            DashboardSMAction.OnRefresh -> {
                onRefresh() // Llama el método de refresco separado
            }
            else -> Unit
        }
    }

    private fun getAllBanners(){
        viewModelScope.launch(Dispatchers.IO){
            val result = bannerRepository.getAllBanners()

            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            banners = UiState.Error(result.error.toImageAndTextError())
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            banners = result.data.toState()
                        )
                    }
                }
            }
        }
    }

    private fun getMyCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMyCustomers()
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            myCustomer = UiState.Error(result.error.toImageAndTextError()),
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update { it.copy(myCustomer = result.data.toState()) }
                }
            }
        }
    }

    // Método de refresco (separa el loading del refreshing)
    private fun onRefresh() {
        _state.update { it.copy(isRefreshing = true) }
        getAllMyReminders()
        getMyCustomer()
        _state.update { it.copy(isRefreshing = false) }
    }

    private fun getAllMyReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = reminderRepository.getAllMyReminders()
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            reminders = UiState.Error(result.error.toImageAndTextError())
                        )
                    }
                }
                is ResultExt.Success -> {

                    val currentMoment = TimeUtils.DateNow
                    val currentMonth = currentMoment.month
                    val currentYear = currentMoment.year

                    val interactions = result.data.filter { interaction ->
                        val reminderDate =
                            Instant.fromEpochMilliseconds(interaction.reminderDate.toLong()).toLocalDateTime(
                                TimeZone.currentSystemDefault()
                            )
                        reminderDate.month == currentMonth && reminderDate.year == currentYear && interaction.isCompleted == true
                    }
                    _state.update {
                        it.copy(
                            totalRemindersMoth = interactions.size.toDouble(),
                            reminders = result.data.toState(),
                        )
                    }
                }
            }
        }
    }
}
