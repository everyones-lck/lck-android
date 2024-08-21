package umc.everyones.lck.domain.model.response.mypage

import umc.everyones.lck.data.dto.response.mypage.ParticipateViewingPartyMypageResponseDto

data class ParticipateViewingPartyMypageModel(
    val viewingParties: List<ParticipateViewingPartyMypageElementModel>,
    val last: Boolean
) {
    data class ParticipateViewingPartyMypageElementModel(
        val id: Int,
        val name: String,
        val date: String
    )
}
