package umc.everyones.lck.data.repositoryImpl.party

import umc.everyones.lck.data.datasource.ViewingPartyDataSource
import umc.everyones.lck.domain.model.request.party.WriteViewingPartyModel
import umc.everyones.lck.domain.model.response.party.JoinViewingPartyModel
import umc.everyones.lck.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
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

    override suspend fun writeViewingParty(request: WriteViewingPartyModel): Result<umc.everyones.lck.domain.model.response.party.WriteViewingPartyModel> =
        runCatching { viewingPartyDataSource.writeViewingParty(request.toWriteViewingPartyRequestDto()).data.toWriteViewingPartyModel() }

    override suspend fun editViewingParty(
        viewingPartyId: Long,
        request: WriteViewingPartyModel
    ): Result<umc.everyones.lck.domain.model.response.party.WriteViewingPartyModel> =
        runCatching { viewingPartyDataSource.editViewingParty(viewingPartyId, request.toWriteViewingPartyRequestDto()).data.toWriteViewingPartyModel() }

}