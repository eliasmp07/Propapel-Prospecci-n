package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.core.data.networking.get
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.customer.CustomerResponse
import org.propapel.prospeccion.root.data.dto.reminder.ReminderResponse
import org.propapel.prospeccion.root.data.mappers.toCustomer
import org.propapel.prospeccion.root.data.mappers.toReminder
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class ReminderRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val httpClient: HttpClient
): ReminderRepository {
    override suspend fun getAllReminder(): ResultExt<List<Reminder>, DataError.Network> {
        val result = httpClient.get<ReminderResponse>(
            route = "/remider/getAllReminders"
        )

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(
                    result.data.reminders.map {
                        it.toReminder()
                    }
                )
            }
        }
    }

    override suspend fun getAllMyReminders(): ResultExt<List<Reminder>, DataError.Network> {
        val result = httpClient.get<ReminderResponse>(
            route = "/remider/getMyReminders/${sessionStorage.get()?.userId?:""}"
        )

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(
                    result.data.reminders.map {
                        it.toReminder()
                    }
                )
            }
        }
    }
}