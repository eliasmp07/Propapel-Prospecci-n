package org.propapel.prospeccion.root.presentation.detailReminderCustomer

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
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class DetailReminderCustomerViewModel(
    private val reminderRepository: ReminderRepository
): ViewModel(){

    private var _state = MutableStateFlow(DetailReminderCustomerState())
    val state: StateFlow<DetailReminderCustomerState> get() = _state.asStateFlow()
    fun getDayReminder(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = reminderRepository.getAllMyReminders()

            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            remindersDay = Reminder(),
                            isLoading = false
                        )
                    }
                }
                is ResultExt.Success -> {
                    val remindersThisDay = result.data.filter { reminder ->
                        reminder.reminderId == id
                    }
                    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

                    today.dayOfWeek

                    _state.update {
                        it.copy(
                            isTodayAppointment = Instant.fromEpochMilliseconds(remindersThisDay.first().reminderDate.toLong())
                                .toLocalDateTime(TimeZone.UTC).date.dayOfMonth == today.dayOfMonth ,
                            date = Instant.fromEpochMilliseconds(remindersThisDay.first().reminderDate.toLong())
                                .toLocalDateTime(TimeZone.UTC).date,
                            remindersDay = remindersThisDay.first(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

}