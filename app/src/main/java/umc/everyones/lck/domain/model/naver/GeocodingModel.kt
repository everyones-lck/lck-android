package umc.everyones.lck.domain.model.naver

import com.naver.maps.geometry.LatLng

data class GeocodingModel(
    val latLng: LatLng,
    val adminAddress: String,
    val roadAddress: String,
    val jibunAddress: String
)