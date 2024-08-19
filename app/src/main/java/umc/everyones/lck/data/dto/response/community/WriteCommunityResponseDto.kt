package umc.everyones.lck.data.dto.response.community

import umc.everyones.lck.domain.model.community.WriteCommunityResponseModel

data class WriteCommunityResponseDto(
    val postId: Long
){
    fun toWriteCommunityResponseModel() =
        WriteCommunityResponseModel(postId)
}