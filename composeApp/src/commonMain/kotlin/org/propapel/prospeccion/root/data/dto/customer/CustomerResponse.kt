package org.propapel.prospeccion.root.data.dto.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.propapel.prospeccion.root.data.dto.reminder.CustomerDto

@Serializable
data class CustomerResponse(
   val customers: List<CustomerDto>
)
