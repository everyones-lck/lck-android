package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.ViewingPartyChatLogModel

data class ViewingPartyChatLogResponseDto(
    val viewingPartyName: String,
    val receiverName: String,
    val receiverTeam: String,
    val chatMessageList: List<ChatLogResponseDto>
){
    data class ChatLogResponseDto(
        val senderId: Long,
        val message: String
    ){
        fun toChatLogModel() =
            ViewingPartyChatLogModel.ChatLogModel(senderId, message)
    }

    fun toViewingPartyChatLogModel() =
        ViewingPartyChatLogModel(viewingPartyName, receiverName, receiverTeam, chatMessageList.map { it.toChatLogModel() })
}
