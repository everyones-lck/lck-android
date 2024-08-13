package umc.everyones.lck.data.dto.response.login

import umc.everyones.lck.domain.model.request.login.LoginAuthUserModel
import umc.everyones.lck.domain.model.response.login.CommonLoginModel

data class CommonLoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTime: String,
    val refreshTokenExpirationTime: String
){
    fun toCommonLoginResponseDto() =
        CommonLoginModel(accessToken, refreshToken, accessTokenExpirationTime, refreshTokenExpirationTime)
}
