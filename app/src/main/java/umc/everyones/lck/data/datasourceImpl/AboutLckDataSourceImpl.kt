package umc.everyones.lck.data.datasourceImpl

import android.os.Build
import androidx.annotation.RequiresApi
import umc.everyones.lck.data.datasource.AboutLckDataSource
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
import umc.everyones.lck.data.service.about_lck.AboutLckService
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel
import javax.inject.Inject

class AboutLckDataSourceImpl @Inject constructor(
    private val aboutLckService: AboutLckService
): AboutLckDataSource {
    override suspend fun fetchLckMatchDetails(
        searchDate: String
    ): BaseResponse<LckMatchDetailsResponseDto> =
        aboutLckService.fetchLckMatchDetails(searchDate)

    override suspend fun fetchLckRanking(
        seasonName: String,
        page: Int,
        size: Int
    ): BaseResponse<LckRankingResponseDto> =
        aboutLckService.fetchLckRanking(seasonName,page,size)

    override suspend fun fetchLckPlayerDetails(
        teamId: Int,
        seasonName: String,
        player_role: LckPlayerDetailsResponseDto.PlayerRole //dto.model
    ): BaseResponse<LckPlayerDetailsResponseDto> =
        aboutLckService.fetchLckPlayerDetails(teamId,seasonName,player_role)

    override suspend fun fetchLckWinningHistory(
        teamId: Int,
        page: Int,
        size: Int
    ): BaseResponse<LckWinningHistoryResponseDto> =
        aboutLckService.fetchLckWinningHistory(teamId, page, size)

    override suspend fun fetchLckRecentPerformances(
        teamId: Int,
        page: Int,
        size: Int
    ): BaseResponse<LckRecentPerformanceResponseDto> =
        aboutLckService.fetchLckRecentPerformances(teamId,page, size)

    override suspend fun fetchLckHistoryOfRoaster(
        teamId: Int,
        page: Int,
        size: Int
    ): BaseResponse<LckHistoryOfRoasterResponseDto> =
        aboutLckService.fetchLckHistoryOfRoaster(teamId, page, size)

    override suspend fun fetchLckWinningCareer(
        playerId: Int,
        page: Int,
        size: Int
    ): BaseResponse<LckWinningCareerResponseDto> =
        aboutLckService.fetchLckWinningCareer(playerId, page, size)

    override suspend fun fetchLckHistory(
        playerId: Int,
        page: Int,
        size: Int
    ): BaseResponse<LckHistoryResponseDto> =
        aboutLckService.fetchLckHistory(playerId, page, size)

    override suspend fun fetchLckPlayer(playerId: Int): BaseResponse<LckPlayerResponseDto> =
        aboutLckService.fetchLckPlayer(playerId)

}