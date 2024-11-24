package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.propapel.prospeccion.core.data.networking.delete
import org.propapel.prospeccion.core.data.networking.get
import org.propapel.prospeccion.core.data.networking.post
import org.propapel.prospeccion.core.data.networking.put
import org.propapel.prospeccion.core.domain.EmptyResult
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.domain.asEmptyDataResult
import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.reminder.ReminderResponse
import org.propapel.prospeccion.root.data.dto.reminder.ReminderResponseDto
import org.propapel.prospeccion.root.data.mappers.toReminder
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class ReminderRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val httpClient: HttpClient,
    private val alarmHandler: AlarmHandler
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

    override suspend fun createReminder(
        reminderDate: Long,
        description: String,
        customerId: Int,
        typeAppointment: String,
    ): ResultExt<Reminder, DataError.Network> {
        val result = httpClient.post<CreateReminderRequest, ReminderResponseDto>(
            route = "/remider/create",
            body = CreateReminderRequest(
                reminderDate = reminderDate.toString(),
                description = description,
                customerId = customerId,
                typeAppointment = typeAppointment
            )
        )
        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                alarmHandler.setRecurringAlarm(
                    reminder = result.data.toReminder()
                )
                ResultExt.Success(result.data.toReminder())
            }
        }
    }

    override suspend fun deleteReminder(reminder: Reminder): EmptyResult<DataError.Network> {
        val result = httpClient.delete<Unit>(
            route = "/remider/delete/${reminder.reminderId}"
        )

        if (result is ResultExt.Success) {
            alarmHandler.cancel(
                reminder = reminder
            )
        }
        return result.asEmptyDataResult()
    }


    override suspend fun updateReminder(reminder: Reminder): EmptyResult<DataError.Network> {
        val result = httpClient.post<UpdateReminderRequest, Unit>(
            route = "/remider/update/${reminder.reminderId}",
            body = UpdateReminderRequest(
                reminderDate = reminder.reminderDate,
                description = reminder.description,
                isComplete = reminder.isCompleted,
                typeAppointment = reminder.typeAppointment
            )
        )
        if (result is ResultExt.Success){
            alarmHandler.setRecurringAlarm(
                reminder = reminder
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun completeReminder(reminderId: Int): EmptyResult<DataError.Network> {
        val result = httpClient.put<Unit, Unit>(
            route = "/remider/completeReminder/$reminderId",
            Unit
        )

        return result.asEmptyDataResult()
    }

}
@Serializable
data class UpdateReminderRequest(
    @SerialName("reminder_date")val reminderDate: String,
    val description: String,
    val typeAppointment: String,
    @SerialName("is_completed") val isComplete: Boolean?,
)
@Serializable
data class CreateReminderRequest(
    @SerialName("reminder_date")val reminderDate: String,
    val description: String,
    val typeAppointment: String,
    val customerId: Int
)