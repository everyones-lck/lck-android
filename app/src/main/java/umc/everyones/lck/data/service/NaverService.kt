package umc.everyones.lck.data.service

import umc.everyones.lck.data.dto.response.naver.geocoding.GeocodingResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import umc.everyones.lck.data.dto.response.naver.reversegeocoding.ReverseGeocodingResponseDto

interface NaverService {
    @GET("map-geocode/v2/geocode")
    suspend fun fetchGeocoding(
        @Query("query") query: String
    ): GeocodingResponseDto

    /*@GET("map-reversegeocode/v2/gc")
    suspend fun fetchReverseGeocodingInfo(
        @Query("coords") coords: String,
        @Query("output") output: String = "json",
        @Query("orders") orders: String = "admcode",
    ): ReverseGeocodingResponseDto*/
}