package umc.everyones.lck.domain.repository.about_lck

import umc.everyones.lck.data.dto.response.about_lck.LckPlayerDetailsResponseDto
import umc.everyones.lck.domain.model.about_lck.AboutLckHistoryModel
import umc.everyones.lck.domain.model.about_lck.AboutLckHistoryOfRoasterModel
import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerModel
import umc.everyones.lck.domain.model.about_lck.AboutLckRankingDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckRecentPerformanceModel
import umc.everyones.lck.domain.model.about_lck.AboutLckWinningCareerModel
import umc.everyones.lck.domain.model.about_lck.AboutLckWinningHistoryModel

interface AboutLckRepository {
    suspend fun fetchLckMatchDetails(searchDate: String): Result<AboutLckMatchDetailsModel>
    suspend fun fetchLckRanking(seasonName: String, page: Int,size: Int): Result<AboutLckRankingDetailsModel>
    suspend fun fetchLckPlayerDetails(teamId: Int, seasonName: String,player_role: LckPlayerDetailsResponseDto.PlayerRole):Result<AboutLckPlayerDetailsModel>
    suspend fun fetchLckWinningHistory(teamId: Int,page: Int,size: Int): Result<AboutLckWinningHistoryModel>
    suspend fun fetchLckRecentPerformances(teamId: Int,page: Int,size: Int): Result<AboutLckRecentPerformanceModel>
    suspend fun fetchLckHistoryOfRoaster(teamId: Int,page: Int,size: Int): Result<AboutLckHistoryOfRoasterModel>
    suspend fun fetchLckWinningCareer(playerId: Int,page: Int,size: Int): Result<AboutLckWinningCareerModel>
    suspend fun fetchLckHistory(playerId: Int,page: Int,size: Int): Result<AboutLckHistoryModel>
    suspend fun fetchLckPlayer(playerId: Int): Result<AboutLckPlayerModel>
}