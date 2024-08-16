package umc.everyones.lck.domain.model.response.party

data class ViewingPartyChatRoomModel(
    val roomId: Long,
    val viewingPartyName: String,
    val sessions: Set<String>
)