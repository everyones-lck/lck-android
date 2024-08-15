package umc.everyones.lck.data.dto.response.home

import umc.everyones.lck.domain.model.response.home.HomeTodayMatchInformationModel

data class HomeTodayMatchResponseDto(
    val todayMatches: List<TodayMatchesDto>,
    val recentMatchResults: List<RecentMatchResultDto>
) {
    data class TodayMatchesDto(
        val matchId: Long,
        val matchDate: String,
        val team1Name: String,
        val team1LogoUrl: String,
        val team2Name: String,
        val team2LogoUrl: String,
        val seasonInfo: String,
        val matchNumber: Int
    ) {
        fun toTodayMatchModel() =
            HomeTodayMatchInformationModel.TodayMatchesModel(matchId, matchDate, team1Name, team1LogoUrl, team2Name, team2LogoUrl, seasonInfo, matchNumber)
    }
    data class RecentMatchResultDto(
        val matchId: Long,
        val matchDate: String,
        val team1Name: String,
        val team1LogoUrl: String,
        val team2Name: String,
        val team2LogoUrl: String,
        val matchResult: String
    ) {
        fun toRecentMatchResultModel() =
            HomeTodayMatchInformationModel.RecentMatchResultModel(matchId, matchDate, team1Name, team1LogoUrl, team2Name, team2LogoUrl, matchResult)
    }
    fun toHomeTodayMatchModel() =
        HomeTodayMatchInformationModel(todayMatches.map { it.toTodayMatchModel() }, recentMatchResults.map { it.toRecentMatchResultModel() })
}
