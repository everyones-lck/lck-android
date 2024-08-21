package umc.everyones.lck.data

data class SignupUserData(
    val kakaoUserId: String,
    val nickName: String,
    val role: String,
    val teamId: Int,
    val tier: String
)