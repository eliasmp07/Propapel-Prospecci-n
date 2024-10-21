package org.propapel.prospeccion.root.presentation.dates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class DatesSMViewModel(
    private val reminderRepository: ReminderRepository
): ViewModel() {
    init {
        viewModelScope.launch {

        }
    }
}