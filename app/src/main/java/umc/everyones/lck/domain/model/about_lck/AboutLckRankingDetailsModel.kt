package umc.everyones.lck.domain.model.about_lck

data class AboutLckRankingDetailsModel(
    val teamDetailList: List<LckTeamRankingDetailsElementDto>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class LckTeamRankingDetailsElementDto(
        val teamId: Int,
        val teamName: String,
        val teamLogoUrl: String,
        val rating: Int
    )
}
