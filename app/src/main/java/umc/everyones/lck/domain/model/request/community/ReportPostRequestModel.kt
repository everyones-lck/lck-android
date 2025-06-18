package umc.everyones.lck.domain.model.request.community

import umc.everyones.lck.data.dto.request.community.ReportPostRequestDto

data class ReportPostRequestModel(
    val postId: Long,
    val reportDetail: String
) {
    fun toDto() = ReportPostRequestDto(postId, reportDetail)
}
