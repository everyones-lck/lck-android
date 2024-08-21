package umc.everyones.lck.data.dto.request.community

import umc.everyones.lck.domain.model.request.community.EditCommunityRequestModel

data class EditCommunityRequestDto(
    val postType: String,
    val postTitle: String,
    val postContent: String
)