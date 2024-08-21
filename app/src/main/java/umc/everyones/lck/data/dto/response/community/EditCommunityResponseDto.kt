package umc.everyones.lck.data.dto.response.community

import umc.everyones.lck.domain.model.response.community.EditCommunityResponseModel

data class EditCommunityResponseDto(
    val postId: Long
){
    fun toEditCommunityResponseModel() =
        EditCommunityResponseModel(postId)
}
