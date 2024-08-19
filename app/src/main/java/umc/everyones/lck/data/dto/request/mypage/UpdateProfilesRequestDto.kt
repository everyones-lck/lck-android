package umc.everyones.lck.data.dto.request.mypage

import okhttp3.MultipartBody
import umc.everyones.lck.data.UpdateProfileRequest

data class UpdateProfilesRequestDto(
    val profileImage: MultipartBody.Part,
    val updateProfileRequest: UpdateProfileRequestElementDto
) {
    data class UpdateProfileRequestElementDto(
        val nickname: String,
        val isDefaultImage: Boolean
    )
}
