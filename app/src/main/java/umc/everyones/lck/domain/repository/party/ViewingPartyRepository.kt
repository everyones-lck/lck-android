package umc.everyones.lck.domain.repository.party

import umc.everyones.lck.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.lck.domain.model.party.ReadViewingPartyModel
import umc.everyones.lck.domain.model.party.ViewingPartyListModel
import umc.everyones.lck.util.network.NetworkResult

interface ViewingPartyRepository {
    suspend fun fetchViewingPartyList(page: Int, size: Int): NetworkResult<ViewingPartyListModel>

    suspend fun fetchViewingParty(viewingPartyId: Long): NetworkResult<ReadViewingPartyModel>
}