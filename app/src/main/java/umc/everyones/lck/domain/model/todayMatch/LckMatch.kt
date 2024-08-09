package umc.everyones.lck.domain.model.todayMatch

data class LckMatch(
    val matchTitle: String,
    val matchDate: String,
    val team1Name: String,
    val team2Name: String,
    val team1LogoResId: Int,
    val team1LogoBlur: Int,
    val team2LogoResId: Int,
    val team2LogoBlur: Int,
    val team1WinRate: String,
    val team2WinRate: String
)
