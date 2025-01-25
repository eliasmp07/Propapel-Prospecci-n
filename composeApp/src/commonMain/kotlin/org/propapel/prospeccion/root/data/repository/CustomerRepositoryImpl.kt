package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.core.data.networking.get
import org.propapel.prospeccion.core.data.networking.post
import org.propapel.prospeccion.core.domain.EmptyResult
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.domain.asEmptyDataResult
import org.propapel.prospeccion.core.domain.repository.AlarmHandler
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.customer.CreateCustomerRequest
import org.propapel.prospeccion.root.data.dto.customer.CustomerResponse
import org.propapel.prospeccion.root.data.dto.customer.InteractionDto
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.data.dto.customer.OpportunityDto
import org.propapel.prospeccion.root.data.dto.customer.ReminderRequest
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.data.dto.customer.UpdateCustomerRequest
import org.propapel.prospeccion.root.data.dto.reminder.CustomerDto
import org.propapel.prospeccion.root.data.mappers.toCustomer
import org.propapel.prospeccion.root.data.mappers.toPurcheDto
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import kotlin.random.Random

class CustomerRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage,
    private val alarmHandler: AlarmHandler
): CustomerRepository {

    override suspend fun create(
        companyName: String,
        contactName: String,
        email: String,
        phoneNumber: String,
        address: String?,
        isOpportunity: Boolean,
        potentialSale: Double,
        status: String,
        typeOfClient: String,
        followUpTasks: String?,
        interactionType: InteractionType,
        purchese: List<PurchaseRequest>,
        interactionDate: Long,
        notes: String,
        reminderDate: Long,
        typeAppointment: String,
        description: String,
        isCompleted: Boolean?
    ): EmptyResult<DataError.Network> {
        var oportunity: List<OpportunityDto>? = null
        var interations: List<InteractionDto>? = null
        var reminderList: List<ReminderRequest>? = null
        if (isOpportunity){
            oportunity = listOf(
                OpportunityDto(
                    opportunityId = 0,
                    isOpportunity = isOpportunity,
                    potentialSale = potentialSale,
                    status = status,
                    followUpTasks = ""
                )
            )
        }
        if (notes.isNotBlank()) {
            interations = listOf(
                InteractionDto(
                    interactionId = 0,
                    opportunityId = 0,
                    interactionType = interactionType.name,
                    interactionDate = interactionDate,
                    notes = notes
                )
            )
        }
        if(description.isNotBlank()){
            reminderList = listOf(
                ReminderRequest(
                    customerId = 0,
                    opportunityId = 0,
                    typeAppointment = typeAppointment,
                    reminderDate =reminderDate,
                    description = description,
                    isCompleted = false
                )
            )
            alarmHandler.setRecurringAlarm(
                Reminder(
                    reminderId = generarNumeroAleatorio(1, 10000),
                    reminderDate =reminderDate.toString(),
                    description = description,
                    customer = Customer(
                        contactName = contactName
                    ),
                    isCompleted = false,
                    typeAppointment = ""
                )
            )
        }
        val result = httpClient.post<CreateCustomerRequest,Unit>(
            route = "/customers/create",
            body = CreateCustomerRequest(
                companyName = companyName,
                contactName = contactName,
                typeClient = typeOfClient,
                email = email,
                phoneNumber = phoneNumber,
                address = address,
                idUser = sessionStorage.get()?.userId?.toInt()?:0,
                opportunities = oportunity,
                purchases = purchese.map {
                    it.toPurcheDto()
                },
                interactions = interations,
                reminders = reminderList
            )
        )
        return result.asEmptyDataResult()
    }

    override suspend fun getAllCustomers(): ResultExt<List<Customer>, DataError.Network> {

        val result = httpClient.get<CustomerResponse>(
            route = "/customers/getAllCustomers"
        )

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(
                    result.data.customers.map {
                        it.toCustomer()
                    }
                )
            }
        }
    }

    override suspend fun getMyCustomers(): ResultExt<List<Customer>, DataError.Network> {

        val result = httpClient.get<CustomerResponse>(
            route = "/customers/myCustomers/${sessionStorage.get()?.userId?.toInt()}"
        )

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(
                    result.data.customers.map {
                        it.toCustomer()
                    }
                )
            }
        }
    }

    override suspend fun updateCustomer(customer: Customer): EmptyResult<DataError.Network> {
        val result = httpClient.post<UpdateCustomerRequest, Unit>(
            route = "/customers/updateCustomer/${customer.idCustomer}",
            body = UpdateCustomerRequest(
                companyName = customer.companyName,
                contactName = customer.contactName,
                email = customer.email,
                phoneNumber = customer.phoneNumber,
                address = customer.address,
                typeOfClient = customer.typeClient
            )
        )

       return result.asEmptyDataResult()
    }

    override suspend fun getCustomerById(customerId: String): ResultExt<Customer, DataError.Network> {
        val result = httpClient.get<CustomerDto>(
            route = "/customers/getCustomerById/$customerId")

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(
                    result.data.toCustomer()
                )
            }
        }
    }
}


fun generarNumeroAleatorio(min: Int, max: Int): Int {
    return Random.nextInt(min, max + 1)
}