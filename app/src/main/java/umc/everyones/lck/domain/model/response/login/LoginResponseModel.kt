package umc.everyones.lck.domain.model.response.login

data class LoginResponseModel(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTime: String,
    val refreshTokenExpirationTime: String,
    val nickName: String
)
