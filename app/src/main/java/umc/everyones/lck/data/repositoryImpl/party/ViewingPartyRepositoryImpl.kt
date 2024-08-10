package umc.everyones.lck.data.repositoryImpl.party

import umc.everyones.lck.data.datasource.ViewingPartyDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.party.JoinViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.lck.data.service.party.ViewingPartyService
import umc.everyones.lck.domain.model.party.JoinViewingPartyModel
import umc.everyones.lck.domain.model.party.ReadViewingPartyModel
import umc.everyones.lck.domain.model.party.ViewingPartyListModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import umc.everyones.lck.util.network.NetworkResult
import umc.everyones.lck.util.network.handleApi
import javax.inject.Inject

class ViewingPartyRepositoryImpl @Inject constructor(
    private val viewingPartyDataSource: ViewingPartyDataSource
) : ViewingPartyRepository {
    override suspend fun fetchViewingPartyList(
        page: Int,
        size: Int
    ): Result<ViewingPartyListModel> = runCatching {
        viewingPartyDataSource.fetchViewingPartyList(
            page,
            size
        ).data.toViewingPartyListModel()
    }

    override suspend fun fetchViewingParty(viewingPartyId: Long): Result<ReadViewingPartyModel> =
        runCatching {
            viewingPartyDataSource.fetchViewingParty(viewingPartyId).data.toReadViewingPartyModel()
        }

    override suspend fun joinViewingParty(viewingPartyId: Long): Result<JoinViewingPartyModel> =
        runCatching { viewingPartyDataSource.joinViewingParty(viewingPartyId).data.toJoinViewingPartyModel() }

}