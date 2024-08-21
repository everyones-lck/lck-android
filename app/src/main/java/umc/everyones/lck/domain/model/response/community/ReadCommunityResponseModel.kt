package umc.everyones.lck.domain.model.response.community

import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto

data class ReadCommunityResponseModel(
    val postType: String,
    val writerProfileUrl: String,
    val writerInfo: String,
    val postTitle: String,
    val postCreatedAt: String,
    val content: String,
    val fileUrlList: List<ReadCommunityResponseDto.File>,
    val commentList: List<CommentListElementModel>
) {
    data class CommentListElementModel(
        val profileUrl: String,
        val writerInfo: String,
        val content: String,
        val createdAt: String,
        val commentId: Long,
        val isWriter: Boolean
    )
}
