package umc.everyones.lck.presentation.lck.data

data class MatchData(
    val matchTitle: String? = null,
    val matchTime: String? = null,
    val teamLogoUrl1: String? = null,
    val teamLogoUrl2: String? = null,
    val isTeam1Winner: Boolean,
    val isTeam2Winner: Boolean
)
