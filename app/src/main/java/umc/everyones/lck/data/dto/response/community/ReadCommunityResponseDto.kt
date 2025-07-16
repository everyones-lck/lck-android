package umc.everyones.lck.data.dto.response.community

import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.util.extension.combineNicknameAndTeam
import umc.everyones.lck.util.extension.toListViewingPartyDateFormat
import umc.everyones.lck.util.extension.toReadDateFormat

data class ReadCommunityResponseDto(
    val postType: String,
    val writerProfileUrl: String,
    val writerNickname: String,
    val writerTeam: String,
    val postTitle: String,
    val postCreatedAt: String,
    val content: String,
    val fileList: List<File>,
    val commentList: List<CommentListElementDto>,
    val isWriter: Boolean
) {
    data class CommentListElementDto(
        val profileImageUrl: String,
        val nickname: String,
        val supportTeam: String,
        val content: String,
        val createdAt: String,
        val commentId: Long,
        val isWriter: Boolean
    ) {
        fun toCommentListElementModel(userNickname: String) =
            ReadCommunityResponseModel.CommentListElementModel(
                profileImageUrl,
                nickname.combineNicknameAndTeam(supportTeam),
                content,
                createdAt.slice(0..15).toListViewingPartyDateFormat(),
                commentId,
                isWriter
            )
    }

    fun toReadCommunityResponseModel(userNickname: String) =
        ReadCommunityResponseModel(
            postType,
            writerProfileUrl,
            writerNickname.combineNicknameAndTeam(writerTeam),
            postTitle,
            postCreatedAt.slice(0..15).toReadDateFormat(),
            content,
            fileList,
            commentList.map { it.toCommentListElementModel(userNickname) },
            isWriter
        )
    data class File(
        val fileUrl: String,
        val isImage: Boolean
    )
}
