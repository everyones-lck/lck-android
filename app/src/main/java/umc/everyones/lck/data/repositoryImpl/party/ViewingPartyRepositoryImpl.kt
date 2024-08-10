package umc.everyones.lck.data.repositoryImpl.party

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.lck.data.service.party.ViewingPartyService
import umc.everyones.lck.domain.model.party.ViewingPartyListModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import umc.everyones.lck.util.network.NetworkResult
import umc.everyones.lck.util.network.handleApi
import javax.inject.Inject

class ViewingPartyRepositoryImpl @Inject constructor(
    private val viewingPartyService: ViewingPartyService
) : ViewingPartyRepository {
    override suspend fun fetchViewingPartyList(
        page: Int,
        size: Int
    ): NetworkResult<ViewingPartyListModel> {
        return handleApi({
            viewingPartyService.fetchViewingPartyList(
                page,
                size
            )
        }) { response: BaseResponse<ViewingPartyListResponseDto> -> response.data.toViewingPartyListModel() }
    }
}