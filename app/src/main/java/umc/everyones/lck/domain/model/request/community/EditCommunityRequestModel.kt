package umc.everyones.lck.domain.model.request.community

import umc.everyones.lck.data.dto.request.community.EditCommunityRequestDto

data class EditCommunityRequestModel(
    val postType: String,
    val postTitle: String,
    val postContent: String
) {
    fun toEditCommunityRequestDto() =
        EditCommunityRequestDto(postType, postTitle, postContent)
}
