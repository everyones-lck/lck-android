package com.umc.ttoklip.data.repository.naver

import com.umc.ttoklip.data.model.naver.geocoding.GeocodingResponse
import umc.everyones.lck.util.network.NetworkResult


interface NaverRepository {
    suspend fun fetchGeocoding(query: String): NetworkResult<GeocodingResponse>
}