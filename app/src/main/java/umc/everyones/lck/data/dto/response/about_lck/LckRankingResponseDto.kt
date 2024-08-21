package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckRankingDetailsModel

data class LckRankingResponseDto(
    val teamDetailList: List<LckTeamRankingDetailsElementDto>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class LckTeamRankingDetailsElementDto(
        val teamId: Int,
        val teamName: String,
        val teamLogoUrl: String,
        val rating: Int
    ){
        fun toAboutLckTeamRankingDetailsElementModel() =
            AboutLckRankingDetailsModel.LckTeamRankingDetailsElementDto(teamId,teamName,teamLogoUrl,rating)
    }
    fun toAboutLckRankingDetailsElementModel() =
        AboutLckRankingDetailsModel(teamDetailList.map{it.toAboutLckTeamRankingDetailsElementModel()}, totalPage,totalElements,isFirst,isLast)
}
