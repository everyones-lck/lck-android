package umc.everyones.lck.data.datasource

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.match.CommonPogRequestDto
import umc.everyones.lck.data.dto.response.match.CommonTodayMatchPogResponseDto
import umc.everyones.lck.data.dto.response.match.MatchPogTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.MatchTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.SetPogTodayMatchResponseDto
import umc.everyones.lck.data.dto.response.match.TodayMatchInformationResponseDto

interface TodayMatchDataSource{
    suspend fun fetchTodayMatchInformation(): BaseResponse<TodayMatchInformationResponseDto>
    suspend fun fetchTodayMatchVoteSetPog(matchId: Long, setIndex: Int): BaseResponse<SetPogTodayMatchResponseDto>
    suspend fun fetchTodayMatchVoteMatch(matchId: Long): BaseResponse<MatchTodayMatchResponseDto>
    suspend fun fetchTodayMatchVoteMatchPog(matchId: Long): BaseResponse<MatchPogTodayMatchResponseDto>
    suspend fun voteTodayMatchSetPog(set: Int, request: CommonPogRequestDto): BaseResponse<CommonTodayMatchPogResponseDto>
    suspend fun voteTodayMatchMatchPog(request: CommonPogRequestDto): BaseResponse<CommonTodayMatchPogResponseDto>
}