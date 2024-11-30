package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.propapel.prospeccion.core.data.networking.post
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.customer.CreatePurchaseRequest
import org.propapel.prospeccion.root.data.dto.customer.InteractionResponseDto
import org.propapel.prospeccion.root.data.mappers.toInteraction
import org.propapel.prospeccion.root.data.mappers.toPurcheDto
import org.propapel.prospeccion.root.domain.models.Interaction
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.repository.InteractionRepository

class InteractionRepositoryImpl(
    private val httpClient: HttpClient
) : InteractionRepository {
    override suspend fun create(
        idCustomer: String,
        interaction: Interaction,
        purchese: List<PurchaseRequest>,
    ): ResultExt<Interaction, DataError.Network> {


        val result = httpClient.post<CreateOnlyInteraction, InteractionResponseDto>(
            route = "/interation/create",
            body = CreateOnlyInteraction(
                interactionType = interaction.interactionType,
                interactionDate = interaction.interactionDate,
                notes = interaction.notes,
                customerId = idCustomer.toInt(),
                purchases = purchese.map { it.toPurcheDto() }
            )
        )

        return when (result) {
            is ResultExt.Success -> ResultExt.Success(result.data.toInteraction())
            is ResultExt.Error -> ResultExt.Error(result.error)
        }
    }
}

@Serializable
data class CreateOnlyInteraction(
    @SerialName("interaction_type") val interactionType: String,
    @SerialName("interaction_date") val interactionDate: Long,
    @SerialName("notes") val notes: String?,
    @SerialName("customerId") val customerId: Int,
    val purchases: List<CreatePurchaseRequest>? = null,
)