package umc.everyones.lck.data.repositoryImpl.about_lck

import umc.everyones.lck.data.datasource.AboutLckDataSource
import umc.everyones.lck.domain.model.about_lck.AboutLckHistoryModel
import umc.everyones.lck.domain.model.about_lck.AboutLckHistoryOfRoasterModel
import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerModel
import umc.everyones.lck.domain.model.about_lck.AboutLckRankingDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckRecentPerformanceModel
import umc.everyones.lck.domain.model.about_lck.AboutLckWinningCareerModel
import umc.everyones.lck.domain.model.about_lck.AboutLckWinningHistoryModel
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository

import javax.inject.Inject

class AboutLckRepositoryImpl @Inject constructor(
    private val aboutLckDataSource: AboutLckDataSource
) : AboutLckRepository {
    override suspend fun fetchLckMatchDetails(
        searchDate: String
    ): Result<AboutLckMatchDetailsModel> =
        runCatching {
            aboutLckDataSource.fetchLckMatchDetails(
                searchDate
            ).data.toAboutLckMatchDetailsModel()
        }


    override suspend fun fetchLckRanking(
        seasonName: String,
        page: Int,
        size: Int
    ): Result<AboutLckRankingDetailsModel> =
        runCatching {
            aboutLckDataSource.fetchLckRanking(
                seasonName,
                page,
                size
            ).data.toAboutLckRankingDetailsElementModel()
        }


    override suspend fun fetchLckPlayerDetails(
        teamId: Int,
        seasonName: String,
        player_role: AboutLckPlayerDetailsModel.PlayerRole
    ): Result<AboutLckPlayerDetailsModel> =
        runCatching {
            aboutLckDataSource.fetchLckPlayerDetails(
                teamId,
                seasonName,
                player_role.toLckPlayerRole()
            ).data.toAboutLckPlayerDetailsModel()
        }


    override suspend fun fetchLckWinningHistory(
        teamId: Int,
        page: Int,
        size: Int
    ): Result<AboutLckWinningHistoryModel> =
        runCatching {
            aboutLckDataSource.fetchLckWinningHistory(
                teamId,
                page,
                size
            ).data.toAboutLckWinningHistoryModel()
        }


    override suspend fun fetchLckRecentPerformances(
        teamId: Int,
        page: Int,
        size: Int
    ): Result<AboutLckRecentPerformanceModel> =
        runCatching {
            aboutLckDataSource.fetchLckRecentPerformances(
                teamId,
                page,
                size
            ).data.toAboutLckRecentPerformanceModel()
        }

    override suspend fun fetchLckHistoryOfRoaster(
        teamId: Int,
        page: Int,
        size: Int
    ): Result<AboutLckHistoryOfRoasterModel> =
        runCatching {
            aboutLckDataSource.fetchLckHistoryOfRoaster(
                teamId,
                page,
                size
            ).data.toAboutLckHistoryOfRoasterModel()
        }

    override suspend fun fetchLckWinningCareer(
        playerId: Int,
        page: Int,
        size: Int
    ): Result<AboutLckWinningCareerModel> =
        runCatching {
            aboutLckDataSource.fetchLckWinningCareer(
                playerId,
                page,
                size
            ).data.toAboutLckWinningCareerModel()
        }

    override suspend fun fetchLckHistory(
        playerId: Int,
        page: Int,
        size: Int
    ): Result<AboutLckHistoryModel> =
        runCatching {
            aboutLckDataSource.fetchLckHistory(
                playerId,
                page,
                size
            ).data.toAboutLckHistoryModel()
        }

    override suspend fun fetchLckPlayer(playerId: Int): Result<AboutLckPlayerModel> =
        runCatching {
        aboutLckDataSource.fetchLckPlayer(
            playerId
        ).data.toAboutLckPlayerModel()
    }
}