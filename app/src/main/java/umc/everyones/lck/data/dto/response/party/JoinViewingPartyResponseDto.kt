package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.JoinViewingPartyModel

data class JoinViewingPartyResponseDto(
    val userId: Long,
    val ownerId: Long
){
    fun toJoinViewingPartyModel() =
        JoinViewingPartyModel(userId, ownerId)
}
