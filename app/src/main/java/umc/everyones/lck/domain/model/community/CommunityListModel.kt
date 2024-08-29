package umc.everyones.lck.domain.model.community

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
        val userProfilePicture: String,
        val commentCounts: Int,
        val thumbnailFileUrl: String
    )
}