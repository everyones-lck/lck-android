package umc.everyones.lck.data.service

import umc.everyones.lck.data.dto.response.naver.geocoding.GeocodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverService {
    @GET("map-geocode/v2/geocode")
    suspend fun fetchGeocoding(
        @Query("query") query: String
    ): Response<GeocodingResponse>
}