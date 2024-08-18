package umc.everyones.lck.data.datasource

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

interface TodayMatchDataSource{
    suspend fun fetchTodayMatchInformation(): BaseResponse<TodayMatchInformationResponseDto>
    suspend fun fetchTodayMatchVoteSetPog(matchId: Long, setIndex: Int): BaseResponse<PogPlayerTodayMatchResponseDto>
    suspend fun fetchTodayMatchVoteMatch(matchId: Long): BaseResponse<MatchTodayMatchResponseDto>
    suspend fun fetchTodayMatchVoteMatchPog(matchId: Long): BaseResponse<PogPlayerTodayMatchResponseDto>
    suspend fun voteTodayMatchSetPog(set: Int, request: CommonPogRequestDto): BaseResponse<CommonTodayMatchPogResponseDto>
    suspend fun voteTodayMatchMatchPog(request: CommonPogRequestDto): BaseResponse<CommonTodayMatchPogResponseDto>
    suspend fun voteSetPog(request: VoteSetPogRequestDto): BaseResponse<CommonVotePogResponseDto>
    suspend fun voteMatch(request: VoteMatchRequestDto): BaseResponse<CommonVotePogResponseDto>
    suspend fun voteMatchPog(request: VoteMatchPogRequestDto): BaseResponse<CommonVotePogResponseDto>
}