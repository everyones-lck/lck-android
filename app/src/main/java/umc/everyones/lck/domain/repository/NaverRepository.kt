package umc.everyones.lck.domain.repository

import umc.everyones.lck.data.dto.response.naver.geocoding.GeocodingResponse
import umc.everyones.lck.util.network.NetworkResult


interface NaverRepository {
    suspend fun fetchGeocoding(query: String): NetworkResult<GeocodingResponse>
}