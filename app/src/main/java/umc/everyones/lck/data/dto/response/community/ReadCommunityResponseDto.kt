package umc.everyones.lck.data.dto.response.community

import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel

data class ReadCommunityResponseDto(
    val postType: String,
    val writerProfileUrl: String,
    val writerNickname: String,
    val writerTeam: String,
    val postTitle: String,
    val postCreatedAt: String,
    val content: String,
    val fileList: List<String>,
    val commentList: List<CommentListElementDto>
) {
    data class CommentListElementDto(
        val profileUrl: String,
        val nickname: String,
        val supportTeam: String,
        val content: String,
        val createdAt: String
    ) {
        fun toCommentListElementModel() =
            ReadCommunityResponseModel.CommentListElementModel(
                profileUrl,
                nickname,
                supportTeam,
                content,
                createdAt
            )
    }

    fun toReadCommunityResponseModel() =
        ReadCommunityResponseModel(
            postType,
            writerProfileUrl,
            writerNickname,
            writerTeam,
            postTitle,
            postCreatedAt,
            content,
            fileList,
            commentList.map { it.toCommentListElementModel() })
}
