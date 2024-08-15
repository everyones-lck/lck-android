package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.MatchTodayMatchModel

data class MatchTodayMatchResponseDto(
    val seasonName: String,
    val matchNumber: Int,
    val team1Id: Int,
    val team1Logo: String,
    val team2Id: Int,
    val team2Logo: String
){
    fun toMatchTodayMatchModel() =
        MatchTodayMatchModel(seasonName, matchNumber, team1Id, team1Logo, team2Id, team2Logo)
}
