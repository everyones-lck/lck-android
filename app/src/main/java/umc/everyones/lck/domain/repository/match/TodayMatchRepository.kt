package umc.everyones.lck.domain.repository.match

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

interface TodayMatchRepository {
    suspend fun fetchTodayMatchInformation(): Result<TodayMatchInformationModel>
    suspend fun fetchTodayMatchVoteSetPog(matchId: Long, setIndex: Int): Result<SetPogTodayMatchModel>
    suspend fun fetchTodayMatchVoteMatch(matchId: Long): Result<MatchTodayMatchModel>
    suspend fun fetchTodayMatchVoteMatchPog(matchId: Long): Result<MatchPogTodayMatchModel>
    suspend fun voteTodayMatchSetPog(set: Int, request: CommonPogModel): Result<CommonTodayMatchPogModel>
    suspend fun voteTodayMatchMatchPog(request: CommonPogModel): Result<CommonTodayMatchPogModel>
}
