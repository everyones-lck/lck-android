package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.CommonVotePogModel

data class CommonVotePogResponseDto(
    val message: String,
    val success: Boolean
) {
    fun toCommonVotePogModel() =
        CommonVotePogModel(message, success)
}
