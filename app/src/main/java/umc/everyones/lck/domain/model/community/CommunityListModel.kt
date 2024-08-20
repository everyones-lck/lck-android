package umc.everyones.lck.domain.model.community

import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto

data class CommunityListModel(
    val postDetailList: List<CommunityListElementModel>,
    val isLast: Boolean
){
    data class CommunityListElementModel(
        val postId: Long,
        val postTitle: String,
        val postCreatedAt: String,
        val userNickname: String,
        val supportTeamName: String,
        val postPicture: String?,
        val commentCounts: Int
    )
}