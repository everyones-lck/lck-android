package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.WriteViewingPartyModel

data class WriteViewingPartyResponseDto(
    val userId: Long,
    val viewingPartyId: Long
){
    fun toWriteViewingPartyModel() =
        WriteViewingPartyModel(userId, viewingPartyId)
}
