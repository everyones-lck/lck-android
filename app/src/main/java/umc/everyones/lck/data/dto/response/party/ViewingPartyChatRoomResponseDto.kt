package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.ViewingPartyChatRoomModel

data class ViewingPartyChatRoomResponseDto(
    val roomId: Long,
    val viewingPartyName: String,
    val sessions: Set<String>
){
    fun toViewingPartyChatRoomModel() =
        ViewingPartyChatRoomModel(roomId, viewingPartyName, sessions)
}