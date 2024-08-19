package umc.everyones.lck.presentation.party.chat

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import umc.everyones.lck.data.dto.request.party.ChatMessage
import javax.inject.Inject

class WsClient @Inject constructor(
    private val viewModel: ViewingPartyChatViewModel,
    private val okHttpClient: OkHttpClient,
    private val request: Request,
    private val spf: SharedPreferences
) : WebSocketListener() {
    private var webSocket: WebSocket? = null
    private var nickname = spf.getString("nickname", "") ?: "알 수 없음"
    private val roomId = viewModel.roomId.value.toInt()

    fun connectWebSocket() {
        webSocket = okHttpClient.newWebSocket(request, this)
    }

    fun enterChatRoom(text: String){
        val gson = Gson()
        val jsonMessage = gson.toJson(
            ChatMessage(
            type = ENTER,
            senderName = nickname,
            chatRoomId = roomId,
            message = text
        )
        )
        webSocket?.send(jsonMessage)
    }

    fun sendMessage(text: String){
        val gson = Gson()
        val jsonMessage = gson.toJson(
            ChatMessage(
            type = TALK,
            senderName = nickname,
            chatRoomId = roomId,
            message = text
        )
        )
        webSocket?.send(jsonMessage)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("WebSocket", "Connection opened: $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WebSocket", "Message received: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: okio.ByteString) {
        Log.d("WebSocket", "Message received (binary): ${bytes.hex()}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("WebSocket", "Connection closing: $code / $reason")
        webSocket.close(code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("WebSocket", "Connection closed: $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocket", "Error: ${t.message}", t)
    }

    companion object {
        private const val ENTER = "ENTER"
        private const val TALK = "TALK"
    }

}