package umc.everyones.lck.domain.model.about_lck


import java.time.LocalDate

data class AboutLckMatchDetailsModel(
    val matchDetailList: List<AboutLckMatchDetailsElementModel>,
    val listSize: Int
) {
    data class AboutLckMatchDetailsElementModel(
        val team1: TeamElementModel,
        val team2: TeamElementModel,
        val matchFinished: Boolean,
        val season: String,
        val matchNumber: Int,
        val matchTime: String,
        val matchDate: String
    ) {
        data class TeamElementModel(
            val teamName: String,
            val teamLogoUrl: String,
            val winner: Boolean
        )
    }
}
