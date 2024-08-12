package umc.everyones.lck.domain.model.response.login

import okhttp3.MultipartBody

data class SignupAuthUserModel(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTime: String,
    val refreshTokenExpirationTime: String
)
