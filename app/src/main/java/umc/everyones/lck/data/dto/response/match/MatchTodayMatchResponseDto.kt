package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.MatchTodayMatchModel

data class MatchTodayMatchResponseDto(
    val seasonName: String,
    val matchNumber: Int,
    val team1Id: Int,
    val team1Name: String,
    val team1Logo: String,
    val team2Id: Int,
    val team2Name: String,
    val team2Logo: String,
    val myVoteTeamId: Int
)
{
    fun toMatchTodayMatchModel() =
        MatchTodayMatchModel(seasonName, matchNumber, team1Id, team1Name, team1Logo, team2Id, team2Name, team2Logo, myVoteTeamId)
}
