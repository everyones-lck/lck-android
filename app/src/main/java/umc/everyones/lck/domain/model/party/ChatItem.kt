package umc.everyones.lck.domain.model.party

data class ChatItem(
    val profileImg: String,
    val message: String,
    val viewType: Int,
    val userId: Int
)