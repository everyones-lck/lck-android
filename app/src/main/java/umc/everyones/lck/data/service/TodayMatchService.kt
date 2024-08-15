package umc.everyones.lck.data.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.match.CommonPogRequestDto
import umc.everyones.lck.data.dto.response.match.CommonTodayMatchPogResponseDto
import umc.everyones.lck.data.dto.response.match.MatchPogTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.MatchTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.SetPogTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.TodayMatchInformationResponseDto

interface TodayMatchService {
    @GET("match/today/information")
    suspend fun fetchTodayMatchInformation(): BaseResponse<TodayMatchInformationResponseDto>

    @GET("votes/set-pog/candidates")
    suspend fun fetchTodayMatchVoteSetPog(
        @Query("match-id") matchId: Long,
        @Query("set-index") setIndex: Int
    ): BaseResponse<SetPogTodayMatchResponseDto>

    @GET("votes/match/candidates")
    suspend fun fetchTodayMatchVoteMatch(
        @Query("match-id") matchId: Long
    ): BaseResponse<MatchTodayMatchResponseDto>

    @GET("votes/match-pog/candidates")
    suspend fun fetchTodayMatchVoteMatchPog(
        @Query("match-id") matchId: Long
    ): BaseResponse<MatchPogTodayMatchResponseDto>

    @POST("pog/set")
    suspend fun voteTodayMatchSetPog(
        @Query("set") set: Int,
        @Body request: CommonPogRequestDto
    ): BaseResponse<CommonTodayMatchPogResponseDto>

    @POST("pog/match")
    suspend fun voteTodayMatchMatchPog(
        @Body request: CommonPogRequestDto
    ): BaseResponse<CommonTodayMatchPogResponseDto>
}