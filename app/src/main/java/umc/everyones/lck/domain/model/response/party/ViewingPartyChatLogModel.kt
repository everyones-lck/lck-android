package umc.everyones.lck.domain.model.response.party

data class ViewingPartyChatLogModel(
    val viewingPartyName: String,
    val receiverName: String,
    val receiverTeam: String,
    val chatMessageList: List<ChatLogModel>
){
    data class ChatLogModel(
        val senderId: Long,
        val message: String
    )
}