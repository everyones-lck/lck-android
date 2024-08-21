package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckRecentPerformanceModel

data class LckRecentPerformanceResponseDto(
    val seasonDetailList: List<LckRecentPerformanceElementDto>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class LckRecentPerformanceElementDto(
        val seasonName : String,
        val rating: Int
    ){
        fun toAboutLckRecentPerformanceElementModel() =
            AboutLckRecentPerformanceModel.LckRecentPerformanceElementModel(seasonName, rating)
    }
    fun toAboutLckRecentPerformanceModel() =
        AboutLckRecentPerformanceModel(seasonDetailList.map{it.toAboutLckRecentPerformanceElementModel()}, totalPage,totalElements,isFirst,isLast )
}