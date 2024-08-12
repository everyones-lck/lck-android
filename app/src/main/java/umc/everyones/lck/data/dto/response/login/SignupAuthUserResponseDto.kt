package umc.everyones.lck.data.dto.response.login

import umc.everyones.lck.domain.model.response.login.SignupAuthUserModel

data class SignupAuthUserResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTime: String,
    val refreshTokenExpirationTime: String
) {
    fun toSignupAuthUserModel() =
        SignupAuthUserModel(
            accessToken,
            refreshToken,
            accessTokenExpirationTime,
            refreshTokenExpirationTime
        )
}