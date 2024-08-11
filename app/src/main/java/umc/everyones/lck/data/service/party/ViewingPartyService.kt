package umc.everyones.lck.data.service.party

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.party.WriteViewingPartyRequestDto
import umc.everyones.lck.data.dto.response.party.JoinViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.lck.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.lck.data.dto.response.party.CommonViewingPartyResponseDto

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

    @POST("viewing/create")
    suspend fun writeViewingParty(
        @Body request: WriteViewingPartyRequestDto
    ): BaseResponse<CommonViewingPartyResponseDto>

    @PATCH("viewing/{viewing_party_id}/update")
    suspend fun editViewingParty(
        @Path("viewing_party_id") viewingPartyId: Long,
        @Body request: WriteViewingPartyRequestDto
    ): BaseResponse<CommonViewingPartyResponseDto>

    @DELETE("viewing/{viewing_party_id}/delete")
    suspend fun deleteViewingParty(
        @Path("viewing_party_id") viewingPartyId: Long
    ): BaseResponse<CommonViewingPartyResponseDto>
}