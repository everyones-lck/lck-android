package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckHistoryModel

data class LckHistoryResponseDto(
    val seasonTeamDetails: List<LckHistoryResponseElementDto>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
){
    data class LckHistoryResponseElementDto(
        val teamName: String,
        val seasonName: String
    ){
        fun toAboutLckHistoryElementModel() =
            AboutLckHistoryModel.LckHistoryElementModel(teamName,seasonName)
    }
    fun toAboutLckHistoryModel() =
        AboutLckHistoryModel(seasonTeamDetails.map{it.toAboutLckHistoryElementModel()}, totalPage,totalElements,isFirst,isLast)
}
