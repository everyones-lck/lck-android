package umc.everyones.lck.data.dto.request.login

import okhttp3.MultipartBody

data class SignupAuthUserRequestDto(
    val profileImage: MultipartBody.Part,
    val signupUserData: SignupUserDataRequestDto
){
    data class SignupUserDataRequestDto(
        val kakaoUserId: String,
        val nickName: String,
        val role: String,
        val teamId: Int
    )
}
