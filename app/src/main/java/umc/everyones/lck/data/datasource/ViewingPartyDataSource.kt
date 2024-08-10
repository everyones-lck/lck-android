package umc.everyones.lck.data.datasource

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.party.WriteViewingPartyRequestDto
import umc.everyones.lck.data.dto.response.party.JoinViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.lck.data.dto.response.party.WriteViewingPartyResponseDto

interface ViewingPartyDataSource {
    suspend fun fetchViewingPartyList(page: Int, size: Int): BaseResponse<ViewingPartyListResponseDto>

    suspend fun fetchViewingParty(viewingPartyId: Long): BaseResponse<ReadViewingPartyResponseDto>

    suspend fun joinViewingParty(viewingPartyId: Long): BaseResponse<JoinViewingPartyResponseDto>

    suspend fun writeViewingParty(requestDto: WriteViewingPartyRequestDto): BaseResponse<WriteViewingPartyResponseDto>
}