package org.propapel.prospeccion.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError

suspend inline fun <reified Response: Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): ResultExt<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}


suspend inline fun <reified Request, reified Response: Any> HttpClient.put(
    route: String,
    body: Request,
    queryParameters: Map<String, Any?> = mapOf()
): ResultExt<Response, DataError.Network> {
    return safeCall {
        put {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
            setBody(body)
        }
    }
}


suspend inline fun <reified Request, reified Response: Any> HttpClient.post(
    route: String,
    body: Request
): ResultExt<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

suspend inline fun <reified Response: Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): ResultExt<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response: Any> HttpClient.delete(
    route: String,
    body: Request
): ResultExt<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

/**
 * Funcion que hace una llamada segura a la Api o servicio
 *
 * @param execute Respuesta que va a ejecutar
 * @return respuesta generica o error para manejarlo en la vista
 */
suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): ResultExt<T, DataError.Network> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        e.printStackTrace()
        return ResultExt.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return ResultExt.Error(DataError.Network.SERIALIZATION)
    } catch(e: Exception) {
        if(e is CancellationException) throw e
        e.printStackTrace()
        return ResultExt.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

/**
 * Manejador de la respuesta
 * @param response Respuesta HTTP de la Api o servicio
 * @return respuesta mapeada como un generico y un error
 */
suspend inline fun <reified T> responseToResult(response: HttpResponse): ResultExt<T, DataError.Network> {
    return when(response.status.value) {
        in 200..299 -> ResultExt.Success(response.body<T>())
        401 -> ResultExt.Error(DataError.Network.UNAUTHORIZED)
        403 -> ResultExt.Error(DataError.Network.FORBIDDEN)
        404 -> ResultExt.Error(DataError.Network.NOT_FOUND)
        408 -> ResultExt.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> ResultExt.Error(DataError.Network.CONFLICT)
        413 -> ResultExt.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> ResultExt.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> ResultExt.Error(DataError.Network.SERVER_ERROR)
        else -> ResultExt.Error(DataError.Network.UNKNOWN)
    }
}

private const val controlProduction: Boolean = true
/**
 * Funcion que crea la ruta de la API
 *
 * @param route Ruta del endpoint
 * @return devuelve el endopoint ya con su ruta
 */

fun constructRoute(route: String): String {
    return when {
        route.contains(URLBackend.detectMode(controlProduction)) -> route
        route.startsWith("/") -> URLBackend.detectMode(controlProduction) + route
        else -> URLBackend.detectMode(controlProduction) + "/$route"
    }
}
