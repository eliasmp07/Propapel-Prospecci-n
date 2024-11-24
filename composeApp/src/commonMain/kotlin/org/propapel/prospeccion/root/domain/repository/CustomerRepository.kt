package org.propapel.prospeccion.root.domain.repository

import org.propapel.prospeccion.core.domain.EmptyResult
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.PurchaseRequest

interface CustomerRepository {
    suspend fun create(
        companyName: String,
        contactName: String,
        email: String,
        phoneNumber: String,
        address: String?,
        isOpportunity: Boolean,
        potentialSale: Double,
        status: String,
        typeOfClient: TypeOfClient,
        followUpTasks: String?,
        interactionType: InteractionType,
        purchese: List<PurchaseRequest>,
        interactionDate: Long,
        notes: String,
        reminderDate: Long,
        description: String,
        typeAppointment: String,
        isCompleted: Boolean?
    ): EmptyResult<DataError.Network>

    suspend fun getAllCustomers(): ResultExt<List<Customer>, DataError.Network>
    suspend fun getMyCustomers(): ResultExt<List<Customer>, DataError.Network>
    suspend fun updateCustomer(customer: Customer): EmptyResult<DataError.Network>
    suspend fun getCustomerById(customerId: String): ResultExt<Customer, DataError.Network>
}