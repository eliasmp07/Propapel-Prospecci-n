package org.propapel.prospeccion.root.data.mappers

import org.propapel.prospeccion.root.data.dto.customer.InteractionResponseDto
import org.propapel.prospeccion.root.domain.models.Interaction

/**
 * Mappers de interacciones
 *
 */

fun InteractionResponseDto.toInteraction(): Interaction {
    return Interaction(
        interactionId = interactionId,
        interactionType = interactionType,
        interactionDate = interactionDate,
        notes = notes
    )
}