package umc.everyones.lck.data.repositoryImpl


import umc.everyones.lck.data.datasource.NaverDataSource
import umc.everyones.lck.data.dto.response.naver.geocoding.GeocodingResponseDto
import umc.everyones.lck.data.dto.response.naver.reversegeocoding.ReverseGeocodingResponseDto
import umc.everyones.lck.data.service.NaverService
import umc.everyones.lck.domain.model.naver.GeocodingModel
import umc.everyones.lck.domain.repository.NaverRepository
import umc.everyones.lck.util.network.NetworkResult
import umc.everyones.lck.util.network.handleApi
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val naverDataSource: NaverDataSource
) : NaverRepository {
    override suspend fun fetchGeocoding(query: String): Result<GeocodingModel> =
        runCatching { naverDataSource.fetchGeocoding(query).toGeocodingModel() }

    /*override suspend fun fetchReverseGeocodingInfo(
        coords: String
    ): NetworkResult<ReverseGeocodingResponseDto> {
        return handleApi({naverService.fetchReverseGeocodingInfo(coords)}) {response: ReverseGeocodingResponseDto -> response}
    }*/
}