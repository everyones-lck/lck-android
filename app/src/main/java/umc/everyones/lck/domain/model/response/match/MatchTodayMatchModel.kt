package umc.everyones.lck.domain.model.response.match

data class MatchTodayMatchModel(
    val seasonName: String,
    val matchNumber: Int,
    val team1Id: Int,
    val team1Logo: String,
    val team2Id: Int,
    val team2Logo: String
)
