package umc.everyones.lck.domain.model.about_lck

data class AboutLckWinningCareerModel(
    val seasonNames: List<String>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
)
