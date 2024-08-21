package umc.everyones.lck.data.repositoryImpl

import umc.everyones.lck.data.datasource.TodayMatchDataSource
import umc.everyones.lck.domain.model.request.match.CommonPogModel
import umc.everyones.lck.domain.model.request.match.VoteMatchModel
import umc.everyones.lck.domain.model.request.match.VoteMatchPogModel
import umc.everyones.lck.domain.model.request.match.VoteSetPogModel
import umc.everyones.lck.domain.model.response.match.CommonTodayMatchPogModel
import umc.everyones.lck.domain.model.response.match.CommonVotePogModel
import umc.everyones.lck.domain.model.response.match.MatchTodayMatchModel
import umc.everyones.lck.domain.model.response.match.PogPlayerTodayMatchModel
import umc.everyones.lck.domain.model.response.match.TodayMatchInformationModel
import umc.everyones.lck.domain.model.response.match.TodayMatchSetCountModel
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
    ): Result<PogPlayerTodayMatchModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchVoteSetPog(
            matchId,
            setIndex
        ).data.toPogPlayerTodayMatchModel()
    }

    override suspend fun fetchTodayMatchVoteMatch(matchId: Long): Result<MatchTodayMatchModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchVoteMatch(matchId).data.toMatchTodayMatchModel()
    }

    override suspend fun fetchTodayMatchVoteMatchPog(matchId: Long): Result<PogPlayerTodayMatchModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchVoteMatchPog(matchId).data.toPogPlayerTodayMatchModel()
    }

    override suspend fun fetchTodayMatchSetPog(
        setIndex: Int,
        request: CommonPogModel,
    ): Result<CommonTodayMatchPogModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchSetPog(
            setIndex,
            request.toCommonPogRequestDto()
        ).data.toCommonTodayMatchPogModel()
    }

    override suspend fun fetchTodayMatchMatchPog(request: CommonPogModel): Result<CommonTodayMatchPogModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchMatchPog(request.toCommonPogRequestDto()).data.toCommonTodayMatchPogModel()
    }

    override suspend fun voteSetPog(request: VoteSetPogModel): Result<CommonVotePogModel> = runCatching {
        todayMatchDataSource.voteSetPog(request.toVoteSetPogRequestDto()).data.toCommonVotePogModel()
    }

    override suspend fun voteMatch(request: VoteMatchModel): Result<CommonVotePogModel> = runCatching {
        todayMatchDataSource.voteMatch(request.toVoteMatchRequestDto()).data.toCommonVotePogModel()
    }

    override suspend fun voteMatchPog(request: VoteMatchPogModel): Result<CommonVotePogModel> = runCatching {
        todayMatchDataSource.voteMatchPog(request.toVoteMatchPogRequestDto()).data.toCommonVotePogModel()
    }

    override suspend fun fetchTodayMatchSetCount(matchId: Long): Result<TodayMatchSetCountModel> = runCatching {
        todayMatchDataSource.fetchTodayMatchSetCount(matchId).data.toTodayMatchSetCountModel()
    }
}