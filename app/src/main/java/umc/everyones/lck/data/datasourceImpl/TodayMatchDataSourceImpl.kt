package umc.everyones.lck.data.datasourceImpl

import umc.everyones.lck.data.datasource.TodayMatchDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.match.CommonPogRequestDto
import umc.everyones.lck.data.dto.request.match.VoteMatchPogRequestDto
import umc.everyones.lck.data.dto.request.match.VoteMatchRequestDto
import umc.everyones.lck.data.dto.request.match.VoteSetPogRequestDto
import umc.everyones.lck.data.dto.response.match.CommonTodayMatchPogResponseDto
import umc.everyones.lck.data.dto.response.match.CommonVotePogResponseDto
import umc.everyones.lck.data.dto.response.match.MatchTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.PogPlayerTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.TodayMatchInformationResponseDto
import umc.everyones.lck.data.dto.response.match.TodayMatchSetCountResponseDto
import umc.everyones.lck.data.service.match.TodayMatchService
import javax.inject.Inject

class TodayMatchDataSourceImpl @Inject constructor(
    private val todayMatchService: TodayMatchService
): TodayMatchDataSource {
    override suspend fun fetchTodayMatchInformation(): BaseResponse<TodayMatchInformationResponseDto> =
        todayMatchService.fetchTodayMatchInformation()

    override suspend fun fetchTodayMatchVoteSetPog(
        matchId: Long,
        setIndex: Int,
    ): BaseResponse<PogPlayerTodayMatchResponseDto> =
        todayMatchService.fetchTodayMatchVoteSetPog(matchId, setIndex)

    override suspend fun fetchTodayMatchVoteMatch(matchId: Long): BaseResponse<MatchTodayMatchResponseDto> =
        todayMatchService.fetchTodayMatchVoteMatch(matchId)

    override suspend fun fetchTodayMatchVoteMatchPog(matchId: Long): BaseResponse<PogPlayerTodayMatchResponseDto> =
        todayMatchService.fetchTodayMatchVoteMatchPog(matchId)

    override suspend fun fetchTodayMatchSetPog(
        setIndex: Int,
        request: CommonPogRequestDto,
    ): BaseResponse<CommonTodayMatchPogResponseDto> =
        todayMatchService.fetchTodayMatchSetPog(setIndex, request)

    override suspend fun fetchTodayMatchMatchPog(request: CommonPogRequestDto): BaseResponse<CommonTodayMatchPogResponseDto> =
        todayMatchService.fetchTodayMatchMatchPog(request)

    override suspend fun voteSetPog(request: VoteSetPogRequestDto): BaseResponse<CommonVotePogResponseDto> =
        todayMatchService.voteSetPog(request)

    override suspend fun voteMatch(request: VoteMatchRequestDto): BaseResponse<CommonVotePogResponseDto> =
        todayMatchService.voteMatch(request)

    override suspend fun voteMatchPog(request: VoteMatchPogRequestDto): BaseResponse<CommonVotePogResponseDto> =
        todayMatchService.voteMatchPog(request)

    override suspend fun fetchTodayMatchSetCount(matchId: Long): BaseResponse<TodayMatchSetCountResponseDto> =
        todayMatchService.fetchTodayMatchSetCount(matchId)
}