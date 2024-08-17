package umc.everyones.lck.data.datasource

import umc.everyones.lck.data.dto.response.naver.geocoding.GeocodingResponseDto
import umc.everyones.lck.data.dto.response.naver.reversegeocoding.ReverseGeocodingResponseDto

interface NaverDataSource {
    suspend fun fetchGeocoding(query: String): GeocodingResponseDto

    //suspend fun fetchReverseGeocodingInfo(coords: String): ReverseGeocodingResponseDto
}