package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckWinningCareerModel

data class LckWinningCareerResponseDto(
    val seasonNames: List<String>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
){
    fun toAboutLckWinningCareerModel() =
        AboutLckWinningCareerModel(seasonNames, totalPage,totalElements,isFirst,isLast )
}
