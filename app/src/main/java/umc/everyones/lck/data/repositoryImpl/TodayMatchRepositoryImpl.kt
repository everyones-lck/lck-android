package umc.everyones.lck.data.repositoryImpl

import umc.everyones.lck.data.datasource.TodayMatchDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.match.CommonPogRequestDto
import umc.everyones.lck.data.dto.response.match.CommonTodayMatchPogResponseDto
import umc.everyones.lck.data.dto.response.match.MatchPogTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.MatchTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.SetPogTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.TodayMatchInformationResponseDto
import umc.everyones.lck.domain.model.request.match.CommonPogModel
import umc.everyones.lck.domain.model.response.match.CommonTodayMatchPogModel
import umc.everyones.lck.domain.model.response.match.MatchPogTodayMatchModel
import umc.everyones.lck.domain.model.response.match.MatchTodayMatchModel
import umc.everyones.lck.domain.model.response.match.SetPogTodayMatchModel
import umc.everyones.lck.domain.model.response.match.TodayMatchInformationModel
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import javax.inject.Inject

class TodayMatchRepositoryImpl @Inject constructor(
    private val todayMatchDataSource: TodayMatchDataSource
): TodayMatchRepository {
    override suspend fun fetchTodayMatchInformation(): Result<TodayMatchInformationModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchInformation().data.toTodayMatchInformationModel()
    }

    override suspend fun fetchTodayMatchVoteSetPog(
        matchId: Long,
        setIndex: Int,
    ): Result<SetPogTodayMatchModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchVoteSetPog(
            matchId,
            setIndex
        ).data.toSetPogTodayMatchModel()
    }

    override suspend fun fetchTodayMatchVoteMatch(matchId: Long): Result<MatchTodayMatchModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchVoteMatch(matchId).data.toMatchTodayMatchModel()
    }

    override suspend fun fetchTodayMatchVoteMatchPog(matchId: Long): Result<MatchPogTodayMatchModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchVoteMatchPog(matchId).data.toMatchPogTodayMatchModel()
    }

    override suspend fun voteTodayMatchSetPog(
        set: Int,
        request: CommonPogModel,
    ): Result<CommonTodayMatchPogModel> = runCatching {
        todayMatchDataSource.voteTodayMatchSetPog(
            set,
            request.toCommonPogRequestDto()
        ).data.toCommonTodayMatchPogModel()
    }

    override suspend fun voteTodayMatchMatchPog(request: CommonPogModel): Result<CommonTodayMatchPogModel> = runCatching {
        todayMatchDataSource.voteTodayMatchMatchPog(request.toCommonPogRequestDto()).data.toCommonTodayMatchPogModel()
    }

}