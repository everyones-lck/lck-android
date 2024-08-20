package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.ViewingPartyChatLogModel
import umc.everyones.lck.presentation.party.adapter.ChatRVA.Companion.RECEIVER
import umc.everyones.lck.presentation.party.adapter.ChatRVA.Companion.SENDER

data class ViewingPartyChatLogResponseDto(
    val viewingPartyName: String,
    val receiverName: String,
    val receiverTeam: String,
    val chatMessageList: List<ChatLogResponseDto>
){
    data class ChatLogResponseDto(
        val senderId: Long,
        val message: String,
    ){
        fun toChatLogModel(isOwner: Boolean): ViewingPartyChatLogModel.ChatLogModel{
            return ViewingPartyChatLogModel.ChatLogModel(senderId, message, if (isOwner) SENDER else RECEIVER)
        }
    }

    fun toViewingPartyChatLogModel(isOwner: Boolean) =
        ViewingPartyChatLogModel(viewingPartyName, receiverName, receiverTeam, chatMessageList.map { it.toChatLogModel(isOwner) })
}
