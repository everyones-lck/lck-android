package umc.everyones.lck.domain.model.request.community

import umc.everyones.lck.data.dto.request.community.ReportCommentRequestDto

data class ReportCommentRequestModel(
    val commentId: Long,
    val reportDetail: String
) {
    fun toDto() =
        ReportCommentRequestDto(commentId, reportDetail)
}
