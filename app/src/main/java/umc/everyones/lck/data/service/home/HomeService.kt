package umc.everyones.lck.data.service.home

import retrofit2.http.GET
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.home.HomeTodayMatchResponseDto

interface HomeService {
    @GET("home/today/information")
    suspend fun fetchHomeTodayMatchInformation(): BaseResponse<HomeTodayMatchResponseDto>
}