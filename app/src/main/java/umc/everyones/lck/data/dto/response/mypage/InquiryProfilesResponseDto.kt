package umc.everyones.lck.data.dto.response.mypage

import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel

data class InquiryProfilesResponseDto(
    val nickname: String,
    val profileImageUrl: String,
    val teamLogoUrl: String,
    val tier: String
){
    fun toInquiryProfilesModel() =
        InquiryProfilesModel(nickname, profileImageUrl, teamLogoUrl, tier)
}
