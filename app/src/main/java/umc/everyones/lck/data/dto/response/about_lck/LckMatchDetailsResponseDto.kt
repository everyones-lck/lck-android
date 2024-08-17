package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
import java.time.LocalDate

data class LckMatchDetailsResponseDto(
    val matchDetailList: List<LckMatchDetailsElementDto>,
    val listSize: Int
) {
    data class LckMatchDetailsElementDto(
        val team1: TeamElementDto,
        val team2: TeamElementDto,
        val matchFinished: Boolean,
        val season: String,
        val matchNumber: Int,
        val matchTime: String
    ){
        data class TeamElementDto(
            val teamName: String,
            val teamLogoUrl: String,
            val winner: Boolean
        ){
            fun toTeamElementModel() =
                AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel.TeamElementModel(teamName, teamLogoUrl, winner)
        }
        fun toAboutLckMatchDetailsElementModel() =
            AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel(team1.toTeamElementModel(),team2.toTeamElementModel(),matchFinished,season,matchNumber,matchTime)
    }
    fun toAboutLckMatchDetailsModel() =
        AboutLckMatchDetailsModel(matchDetailList.map{it.toAboutLckMatchDetailsElementModel()}, listSize)
}