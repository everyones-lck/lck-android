package umc.everyones.lck.domain.repository.party

import umc.everyones.lck.domain.model.request.party.WriteViewingPartyModel
import umc.everyones.lck.domain.model.response.party.JoinViewingPartyModel
import umc.everyones.lck.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel

interface ViewingPartyRepository {
    suspend fun fetchViewingPartyList(page: Int, size: Int): Result<ViewingPartyListModel>

    suspend fun fetchViewingParty(viewingPartyId: Long): Result<ReadViewingPartyModel>

    suspend fun joinViewingParty(viewingPartyId: Long): Result<JoinViewingPartyModel>

    suspend fun writeViewingParty(request: WriteViewingPartyModel): Result<umc.everyones.lck.domain.model.response.party.WriteViewingPartyModel>
}