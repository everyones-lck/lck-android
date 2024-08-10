package umc.everyones.lck.data.service.party

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.party.JoinViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ViewingPartyListResponseDto

interface ViewingPartyService {
    @GET("viewing/list")
    suspend fun fetchViewingPartyList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<ViewingPartyListResponseDto>

    @GET("viewing/{viewing_party_id}/detail")
    suspend fun fetchViewingParty(
        @Path("viewing_party_id") viewingPartyId: Long
    ): BaseResponse<ReadViewingPartyResponseDto>

    @POST("viewing/{viewing_party_id}/join")
    suspend fun joinViewingParty(
        @Path("viewing_party_id") viewingPartyId: Long
    ): BaseResponse<JoinViewingPartyResponseDto>
}