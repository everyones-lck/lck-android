package umc.everyones.lck.data.dto.response.login

import umc.everyones.lck.domain.model.response.login.CommonLoginResponseModel

data class CommonLoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTime: String,
    val refreshTokenExpirationTime: String
){
    fun toCommonLoginResponseDto() =
        CommonLoginResponseModel(accessToken, refreshToken, accessTokenExpirationTime, refreshTokenExpirationTime)
}
