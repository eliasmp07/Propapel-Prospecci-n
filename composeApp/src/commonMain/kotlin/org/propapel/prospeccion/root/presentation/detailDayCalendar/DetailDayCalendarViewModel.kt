package org.propapel.prospeccion.root.presentation.detailDayCalendar

import androidx.lifecycle.SavedStateHandle
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
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class DetailDayCalendarViewModel(
    private val reminderRepository: ReminderRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _state = MutableStateFlow(DetailDayCalendarState())
    val state: StateFlow<DetailDayCalendarState> get() = _state.asStateFlow()


    private val dateMoment: Long = checkNotNull( savedStateHandle[""])

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = reminderRepository.getAllMyReminders()

            when(result){
                is ResultExt.Error -> {
                    _state.update { it.copy(myReminders = listOf()) }
                }
                is ResultExt.Success -> {
                    // Obtiene el momento actual (con la zona horaria del sistema)
                    val currentMoment =  Instant.fromEpochMilliseconds(dateMoment).toLocalDateTime(TimeZone.currentSystemDefault())
                    val currentMonth = currentMoment.dayOfMonth
                    val currentYear = currentMoment.year

                    // Filtra los recordatorios que sean del mes y año actuales
                    val remindersThisDay = result.data.filter { reminder ->
                        val reminderDate = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong()).toLocalDateTime(
                            TimeZone.currentSystemDefault())
                        reminderDate.dayOfMonth == currentMonth && reminderDate.year == currentYear
                    }
                    _state.update {
                        it.copy(myReminders = remindersThisDay)
                    }
                }
            }
        }
    }
}