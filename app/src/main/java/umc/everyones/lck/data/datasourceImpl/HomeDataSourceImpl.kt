package umc.everyones.lck.data.datasourceImpl

import umc.everyones.lck.data.datasource.HomeDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.home.HomeTodayMatchResponseDto
import umc.everyones.lck.data.service.home.HomeService
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeService: HomeService
): HomeDataSource {
    override suspend fun fetchHomeTodayMatchInformation(): BaseResponse<HomeTodayMatchResponseDto> =
        homeService.fetchHomeTodayMatchInformation()
}