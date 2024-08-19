package umc.everyones.lck.data.datasourceImpl.naver

import umc.everyones.lck.data.datasource.NaverDataSource
import umc.everyones.lck.data.dto.response.naver.geocoding.GeocodingResponseDto
import umc.everyones.lck.data.dto.response.naver.reversegeocoding.ReverseGeocodingResponseDto
import umc.everyones.lck.data.service.NaverService
import javax.inject.Inject

class NaverDataSourceImpl @Inject constructor(
    private val naverService: NaverService
): NaverDataSource {
    override suspend fun fetchGeocoding(query: String): GeocodingResponseDto =
        naverService.fetchGeocoding(query)

    /*override suspend fun fetchReverseGeocodingInfo(coords: String): ReverseGeocodingResponseDto =
        naverService.fetchReverseGeocodingInfo(coords)*/
}