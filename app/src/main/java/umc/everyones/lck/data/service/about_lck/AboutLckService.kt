package umc.everyones.lck.data.service.about_lck

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.about_lck.LckHistoryOfRoasterResponseDto
import umc.everyones.lck.data.dto.response.about_lck.LckHistoryResponseDto
import umc.everyones.lck.data.dto.response.about_lck.LckMatchDetailsResponseDto
import umc.everyones.lck.data.dto.response.about_lck.LckPlayerDetailsResponseDto
import umc.everyones.lck.data.dto.response.about_lck.LckPlayerResponseDto
import umc.everyones.lck.data.dto.response.about_lck.LckRankingResponseDto
import umc.everyones.lck.data.dto.response.about_lck.LckRecentPerformanceResponseDto
import umc.everyones.lck.data.dto.response.about_lck.LckWinningCareerResponseDto
import umc.everyones.lck.data.dto.response.about_lck.LckWinningHistoryResponseDto
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel

interface AboutLckService {
    @GET("aboutlck/match")
    suspend fun fetchLckMatchDetails(
        @Query("searchDate") searchDate: String // "yyyy-mm-dd" 형식의 날짜
    ): BaseResponse<LckMatchDetailsResponseDto>

    @GET("aboutlck/team/rating")
    suspend fun fetchLckRanking(
        @Query("seasonName") seasonName: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<LckRankingResponseDto>

    @GET("aboutlck/team/{teamId}/player-information")
    suspend fun fetchLckPlayerDetails(
        @Path("teamId") teamId: Int,
        @Query("seasonName") seasonName: String,
        @Query("player_role") player_role: LckPlayerDetailsResponseDto.PlayerRole
    ): BaseResponse<LckPlayerDetailsResponseDto>

    @GET("aboutlck/team/{teamId}/winning-history")
    suspend fun fetchLckWinningHistory(
        @Path("teamId") teamId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<LckWinningHistoryResponseDto>

    @GET("aboutlck/team/{teamId}/rating-history")
    suspend fun fetchLckRecentPerformances(
        @Path("teamId") teamId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<LckRecentPerformanceResponseDto>

    @GET("aboutlck/team/{teamId}/player-history")
    suspend fun fetchLckHistoryOfRoaster(
        @Path("teamId") teamId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<LckHistoryOfRoasterResponseDto>

    @GET("aboutlck/player/{playerId}/winning-history")
    suspend fun fetchLckWinningCareer(
        @Path("playerId") playerId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<LckWinningCareerResponseDto>

    @GET("aboutlck/player/{playerId}/team-history")
    suspend fun fetchLckHistory(
        @Path("playerId") playerId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<LckHistoryResponseDto>

    @GET("aboutlck/player/{playerId}/information")
    suspend fun fetchLckPlayer(
        @Path("playerId") playerId: Int,
    ): BaseResponse<LckPlayerResponseDto>

}