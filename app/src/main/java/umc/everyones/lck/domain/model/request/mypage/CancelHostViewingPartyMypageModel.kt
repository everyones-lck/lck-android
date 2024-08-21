package umc.everyones.lck.domain.model.request.mypage

import umc.everyones.lck.data.dto.request.mypage.CancelHostViewingPartyMypageRequestDto
import umc.everyones.lck.data.dto.request.mypage.CancelParticipateViewingPartyMypageRequestDto
import java.io.Serializable

data class CancelHostViewingPartyMypageModel(
    val viewingPartyId: Int
): Serializable {
    fun toCancelHostViewingPartyMypageRequestDto() =
        CancelHostViewingPartyMypageRequestDto(viewingPartyId)
}
