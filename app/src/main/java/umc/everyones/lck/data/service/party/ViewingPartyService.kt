package umc.everyones.lck.data.service.party

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.party.ViewingPartyListResponseDto

interface ViewingPartyService {
    @GET("viewing/list")
    suspend fun fetchViewingPartyList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<BaseResponse<ViewingPartyListResponseDto>>
}