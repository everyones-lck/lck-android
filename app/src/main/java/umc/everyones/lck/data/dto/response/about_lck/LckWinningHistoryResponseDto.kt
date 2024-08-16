package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckWinningHistoryModel

data class LckWinningHistoryResponseDto (
    val seasonNameList: List<String>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    fun toAboutLckWinningHistoryModel() =
        AboutLckWinningHistoryModel(seasonNameList, totalPage,totalElements,isFirst,isLast)
}