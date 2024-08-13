package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.CommonViewingPartyModel

data class CommonViewingPartyResponseDto(
    val userId: Long,
    val viewingPartyId: Long
){
    fun toCommonViewingPartyModel() =
        CommonViewingPartyModel(userId, viewingPartyId)
}
