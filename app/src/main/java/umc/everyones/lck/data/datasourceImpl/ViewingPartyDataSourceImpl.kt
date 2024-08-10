package umc.everyones.lck.data.datasourceImpl

import umc.everyones.lck.data.datasource.ViewingPartyDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.party.JoinViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.lck.data.service.party.ViewingPartyService
import javax.inject.Inject

class ViewingPartyDataSourceImpl @Inject constructor(
    private val viewingPartyService: ViewingPartyService
): ViewingPartyDataSource {
    override suspend fun fetchViewingPartyList(
        page: Int,
        size: Int
    ): BaseResponse<ViewingPartyListResponseDto> {
        return viewingPartyService.fetchViewingPartyList(page, size)
    }

    override suspend fun fetchViewingParty(viewingPartyId: Long): BaseResponse<ReadViewingPartyResponseDto> {
        return viewingPartyService.fetchViewingParty(viewingPartyId)
    }

    override suspend fun joinViewingParty(viewingPartyId: Long): BaseResponse<JoinViewingPartyResponseDto> {
        return viewingPartyService.joinViewingParty(viewingPartyId)
    }

}