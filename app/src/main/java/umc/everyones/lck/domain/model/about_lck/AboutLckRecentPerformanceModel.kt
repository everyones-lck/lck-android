package umc.everyones.lck.domain.model.about_lck

data class AboutLckRecentPerformanceModel(
    val seasonDetailList: List<LckRecentPerformanceElementModel>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class LckRecentPerformanceElementModel(
        val seasonName : String,
        val rating: Int
    )
}