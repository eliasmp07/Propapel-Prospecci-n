package org.propapel.prospeccion.root.presentation.homeRoot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class HomeRootViewModel(
    private val sessionStorage: SessionStorage,
    private val reminderRepository: ReminderRepository
): ViewModel() {
    private var _state = MutableStateFlow(HomeSMRootState())
    val state: StateFlow<HomeSMRootState> get() = _state.asStateFlow()
    init {
        getAllMyReminders()
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    user = sessionStorage.get()?: AuthInfo()
                )
            }
        }
    }



    private fun getAllMyReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = reminderRepository.getAllMyReminders()
            when (result) {
                is ResultExt.Error -> {
                    _state.update { it.copy(reminders = listOf()) }
                }
                is ResultExt.Success -> {
                    val currentMoment = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val currentMonth = currentMoment.month
                    val currentYear = currentMoment.year
                    val currentDay = currentMoment.dayOfMonth

                    val remindersThisMonth = result.data.filter { reminder ->
                        val reminderDate = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong()).toLocalDateTime(
                            TimeZone.currentSystemDefault())
                        reminderDate.dayOfMonth == currentDay && reminderDate.year == currentYear && reminderDate.month == currentMonth && reminder.isCompleted == false
                    }

                    _state.update { it.copy(reminders = remindersThisMonth) }
                }
            }
        }
    }

    fun onAction(action: HomeRootAction){
        when(action){
            HomeRootAction.OnLogoutClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    sessionStorage.set(null)
                }
            }
        }
    }

}