package umc.everyones.lck.data.dto.response.mypage

import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel

data class InquiryProfilesResponseDto(
    val nickname: String,
    val profileImageUrl: String,
    val teamId: Int,
    val tier: String
){
    fun toInquiryProfilesModel() =
        InquiryProfilesModel(nickname, profileImageUrl, teamId, tier)
}
