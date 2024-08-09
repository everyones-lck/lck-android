package umc.everyones.lck.data.repositoryImpl


import umc.everyones.lck.data.dto.response.naver.geocoding.GeocodingResponse
import umc.everyones.lck.data.service.NaverService
import umc.everyones.lck.domain.repository.NaverRepository
import umc.everyones.lck.util.network.NetworkResult
import umc.everyones.lck.util.network.handleApi
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val naverService: NaverService
) : NaverRepository {
    override suspend fun fetchGeocoding(query: String): NetworkResult<GeocodingResponse> {
        return handleApi({naverService.fetchGeocoding(query)}) {response: GeocodingResponse -> response}
    }
}