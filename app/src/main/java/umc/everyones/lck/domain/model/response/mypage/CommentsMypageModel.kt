package umc.everyones.lck.domain.model.response.mypage

data class CommentsMypageModel(
    val comments: List<CommentsMypageElementModel>,
    val isLast: Boolean
) {
    data class CommentsMypageElementModel(
        val id: Int,
        val content: String,
        val postType: String
    )
}
