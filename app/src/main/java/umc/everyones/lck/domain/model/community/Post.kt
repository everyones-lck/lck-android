package umc.everyones.lck.domain.model.community

data class Post(
    val postId: Int,
    val title: String,
    val date: String,
    val nickname: String,
    val favoriteTeam: String
)
