package org.propapel.prospeccion.root.presentation.dates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class DatesSMViewModel(
    private val reminderRepository: ReminderRepository
): ViewModel() {

    private val _state = MutableStateFlow(DatesSMState())
    val state:StateFlow<DatesSMState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = reminderRepository.getAllMyReminders()
            when(result){
                is ResultExt.Error ->{
                    _state.update {
                        it.copy(
                            datesReminders = listOf()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        val dates = result.data.map {
                            Instant.fromEpochMilliseconds(it.reminderDate.toLong()).toLocalDateTime(TimeZone.UTC).date
                        }
                        it.copy(
                            reminders = result.data,
                            datesReminders = dates
                        )
                    }
                }
            }
        }
    }
}