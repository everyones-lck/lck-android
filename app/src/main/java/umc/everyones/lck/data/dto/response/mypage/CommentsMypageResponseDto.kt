package umc.everyones.lck.data.dto.response.mypage

import umc.everyones.lck.domain.model.response.mypage.CommentsMypageModel
import umc.everyones.lck.domain.model.response.mypage.PostsMypageModel

data class CommentsMypageResponseDto(
    val comments: List<CommentsMypageResponseElementDto>,
    val isLast: Boolean
) {
    data class CommentsMypageResponseElementDto(
        val id: Int,
        val title: String,
        val postType: String
    ) {
        fun toCommentsMypageElementModel() =
            CommentsMypageModel.CommentsMypageElementModel(id, title, postType)
    }

    fun toCommentsMypageModel() =
        CommentsMypageModel(comments.map { it.toCommentsMypageElementModel() }, isLast)
}
