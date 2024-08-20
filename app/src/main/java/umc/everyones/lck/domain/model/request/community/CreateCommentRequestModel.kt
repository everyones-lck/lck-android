package umc.everyones.lck.domain.model.request.community

import umc.everyones.lck.data.dto.request.community.CreateCommentRequestDto

data class CreateCommentRequestModel(
    val content: String
){
    fun toCreateCommentRequestDto() =
        CreateCommentRequestDto(content)
}
