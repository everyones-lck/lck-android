package umc.everyones.lck.data.dto.response.community

import umc.everyones.lck.domain.model.community.CommunityListModel

data class CommunityListResponseDto(
    val postDetailList: List<CommunityListElementDto>,
    val isLast: Boolean
) {
    data class CommunityListElementDto(
        val postId: Long,
        val postTitle: String,
        val postCreatedAt: String,
        val userNickname: String,
        val supportTeamName: String,
        val postPicture: String,
        val commentCounts: Int
    ) {
        fun toCommunityListElementModel() =
            CommunityListModel.CommunityListElementModel(
                postId,
                postTitle,
                postCreatedAt,
                userNickname,
                if (supportTeamName == "empty") "" else supportTeamName,
                postPicture,
                commentCounts
            )
    }

    fun toCommunityListModel() =
        CommunityListModel(postDetailList.map { it.toCommunityListElementModel() }, isLast)
}