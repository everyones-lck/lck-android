package umc.everyones.lck.domain.repository.home

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.home.HomeTodayMatchResponseDto
import umc.everyones.lck.domain.model.response.home.HomeTodayMatchModel

interface HomeRepository {
    suspend fun fetchHomeTodayMatchInformation(): Result<HomeTodayMatchModel>
}