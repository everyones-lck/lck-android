package umc.everyones.lck.domain.model.request.party

import umc.everyones.lck.data.dto.response.party.ReportViewingPartyRequestDto

data class ReportViewingPartyModel(
    val viewingPartyId: Long,
    val reportDetail: String
) {
    fun toDto() = ReportViewingPartyRequestDto(
        viewingPartyId, reportDetail
    )
}
