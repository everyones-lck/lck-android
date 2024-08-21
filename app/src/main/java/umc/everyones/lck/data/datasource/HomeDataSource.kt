package umc.everyones.lck.data.datasource

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.home.HomeTodayMatchResponseDto

interface HomeDataSource {
    suspend fun fetchHomeTodayMatchInformation(): BaseResponse<HomeTodayMatchResponseDto>
}