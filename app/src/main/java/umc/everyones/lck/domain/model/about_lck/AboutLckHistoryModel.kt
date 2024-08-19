package umc.everyones.lck.domain.model.about_lck


data class AboutLckHistoryModel(
    val seasonTeamDetails: List<LckHistoryElementModel>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
){
    data class LckHistoryElementModel(
        val teamName: String,
        val seasonName: String
    )
}
