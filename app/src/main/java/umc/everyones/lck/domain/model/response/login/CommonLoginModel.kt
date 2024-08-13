package umc.everyones.lck.domain.model.response.login

data class CommonLoginModel(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTime: String,
    val refreshTokenExpirationTime: String
)
