package umc.everyones.lck

data class User(
    val nickname: String = "",
    val profileUri: String = "",
    val team: String = "default_team",
    val tier: String = "Bronze",
    val userId: Int = 0
)
