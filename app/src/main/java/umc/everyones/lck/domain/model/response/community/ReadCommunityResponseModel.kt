package umc.everyones.lck.domain.model.response.community

data class ReadCommunityResponseModel(
    val postType: String,
    val writerProfileUrl: String,
    val writerNickname: String,
    val writerTeam: String,
    val postTitle: String,
    val postCreatedAt: String,
    val content: String,
    val fileUrlList: List<String>,
    val commentList: List<CommentListElementModel>
) {
    data class CommentListElementModel(
        val profileUrl: String,
        val nickname: String,
        val supportTeam: String,
        val content: String,
        val createdAt: String
    )
}
