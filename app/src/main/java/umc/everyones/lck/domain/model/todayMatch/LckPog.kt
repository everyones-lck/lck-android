package umc.everyones.lck.domain.model.todayMatch

data class LckPog(
    val matchTitle: String,
    val matchDate: String,
    val players: List<Player>
)
data class Player(
    val name: String,
    val profileImageResId: Int
)
