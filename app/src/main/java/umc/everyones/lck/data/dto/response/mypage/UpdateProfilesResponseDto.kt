package umc.everyones.lck.data.dto.response.mypage

import umc.everyones.lck.domain.model.response.mypage.UpdateProfilesModel

data class UpdateProfilesResponseDto(
    val  updatedProfileImageUrl: String,
    val updatedNickname: String
){
    fun toUpdateProfilesModel() =
        UpdateProfilesModel(updatedProfileImageUrl, updatedNickname)
}
