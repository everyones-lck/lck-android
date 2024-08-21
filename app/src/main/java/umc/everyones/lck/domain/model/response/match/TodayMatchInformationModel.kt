package umc.everyones.lck.domain.model.response.match

data class TodayMatchInformationModel (
    val matchResponses: List<MatchResponsesModel>,
    val matchResponseSize: Int
) {
    data class MatchResponsesModel(
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
    )
}
