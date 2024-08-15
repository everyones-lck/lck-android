package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.TodayMatchInformationModel

data class TodayMatchInformationResponseDto(
    val matchResponses: List<MatchResponsesDto>,
    val matchResponseSize: Int
) {
    data class MatchResponsesDto(
        val matchId : Long,
        val matchDate : String,
        val team1Name: String,
        val team1LogoUrl: String,
        val team2Name: String,
        val team2LogoUrl: String,
        val team1VoteRate: Double,
        val team2VoteRate: Double,
        val seasonInfo: String,
        val matchNumber: Int
    ) {
        fun toMatchResponseModel() =
            TodayMatchInformationModel.MatchModel(matchId, matchDate, team1Name, team1LogoUrl, team2Name, team2LogoUrl, team1VoteRate, team2VoteRate, seasonInfo, matchNumber)
    }
    fun toTodayMatchInformationModel() =
        TodayMatchInformationModel(matchResponses.map { it.toMatchResponseModel() }, matchResponseSize)

}
