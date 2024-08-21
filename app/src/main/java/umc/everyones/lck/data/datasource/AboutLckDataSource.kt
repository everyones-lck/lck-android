package umc.everyones.lck.data.datasource

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

interface AboutLckDataSource {
    suspend fun fetchLckMatchDetails(searchDate: String): BaseResponse<LckMatchDetailsResponseDto>
    suspend fun fetchLckRanking(seasonName: String, page: Int,size: Int):BaseResponse<LckRankingResponseDto>
    suspend fun fetchLckPlayerDetails(teamId: Int,seasonName: String,player_role: LckPlayerDetailsResponseDto.PlayerRole):BaseResponse<LckPlayerDetailsResponseDto>
    suspend fun fetchLckWinningHistory(teamId: Int, page: Int,size: Int): BaseResponse<LckWinningHistoryResponseDto>
    suspend fun fetchLckRecentPerformances(teamId: Int, page: Int,size: Int): BaseResponse<LckRecentPerformanceResponseDto>
    suspend fun fetchLckHistoryOfRoaster(teamId: Int, page: Int,size: Int): BaseResponse<LckHistoryOfRoasterResponseDto>
    suspend fun fetchLckWinningCareer(playerId: Int,page: Int,size: Int): BaseResponse<LckWinningCareerResponseDto>
    suspend fun fetchLckHistory(playerId: Int,page: Int,size: Int): BaseResponse<LckHistoryResponseDto>
    suspend fun fetchLckPlayer(playerId: Int): BaseResponse<LckPlayerResponseDto>
}
