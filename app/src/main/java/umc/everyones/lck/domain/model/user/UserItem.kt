package umc.everyones.lck.domain.model.user

data class UserItem(
    val kakaoUserId: String,
    val nickname: String,
    val profileUri: String,  // profileUri 필드 추가
    val teamId: String,
    val tier: String
)
