package umc.everyones.lck.domain.model.about_lck

data class AboutLckWinningHistoryModel (
    val seasonNameList: List<String>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
)