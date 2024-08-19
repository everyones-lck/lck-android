package umc.everyones.lck.data.dto.response.mypage

import umc.everyones.lck.domain.model.response.mypage.PostsMypageModel

data class PostsMypageResponseDto(
    val id: Int,
    val title: String,
    val postType: String
){
    fun toPostsMypageModel() =
        PostsMypageModel(id, title, postType)
}
