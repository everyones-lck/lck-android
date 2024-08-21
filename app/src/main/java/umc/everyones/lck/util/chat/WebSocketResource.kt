package umc.everyones.lck.util.chat

import okhttp3.Response

sealed class WebSocketResource {
    data object Open :WebSocketResource()
    data object Enter : WebSocketResource()
    data class Closing(val reason: String) : WebSocketResource()
    data class Failure(val response: Response?) : WebSocketResource()
}