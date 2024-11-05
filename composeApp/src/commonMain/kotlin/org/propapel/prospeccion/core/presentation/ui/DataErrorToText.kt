package org.propapel.prospeccion.core.presentation.ui

import org.propapel.prospeccion.core.domain.utils.DataError
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.error_disk_full
import prospeccion.composeapp.generated.resources.error_no_internet
import prospeccion.composeapp.generated.resources.error_payload_too_large
import prospeccion.composeapp.generated.resources.error_request_timeout
import prospeccion.composeapp.generated.resources.error_serialization
import prospeccion.composeapp.generated.resources.error_server_error
import prospeccion.composeapp.generated.resources.error_too_many_requests
import prospeccion.composeapp.generated.resources.error_unknown

fun DataError.asUiText(): UiText {
    return when(this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(
            Res.string.error_disk_full
        )
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            Res.string.error_request_timeout
        )
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            Res.string.error_too_many_requests
        )
        DataError.Network.NO_INTERNET -> UiText.StringResource(
            Res.string.error_no_internet
        )
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            Res.string.error_payload_too_large
        )
        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            Res.string.error_server_error
        )
        DataError.Network.SERIALIZATION -> UiText.StringResource(
            Res.string.error_serialization
        )
        else -> UiText.StringResource(Res.string.error_unknown)
        /*

        DataError.Network.UNAUTHORIZED -> TODO()
        DataError.Network.CONFLICT -> TODO()
        DataError.Network.FORBIDDEN -> TODO()
        DataError.Network.NOT_FOUND -> TODO()
        DataError.Network.UNKNOWN -> TODO()
         */
    }
}