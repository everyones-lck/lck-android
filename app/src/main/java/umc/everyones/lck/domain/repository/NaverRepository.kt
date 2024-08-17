package umc.everyones.lck.domain.repository

import umc.everyones.lck.data.dto.response.naver.geocoding.GeocodingResponseDto
import umc.everyones.lck.domain.model.naver.GeocodingModel
import umc.everyones.lck.domain.model.naver.ReverseGeocodingModel


interface NaverRepository {
    suspend fun fetchGeocoding(query: String): Result<GeocodingModel>

    //suspend fun fetchReverseGeocodingInfo(coords: String): Result<ReverseGeocodingModel>
}